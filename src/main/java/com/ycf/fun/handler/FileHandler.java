package com.ycf.fun.handler;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

public class FileHandler
{
    @SuppressWarnings({"unchecked", "rawtypes"})
    public static Map<String, Object> getAllParamValues(HttpServletRequest request)
    {
        // System.out.println(request.getSession().getServletContext().getRealPath("/tmp"));
        boolean isMultipart = ServletFileUpload.isMultipartContent(request);
        if (!isMultipart)
        {
            return (Map)request.getParameterMap();
        }
        else
        {
            FileItemFactory factory = new DiskFileItemFactory();
            
            // Create a new file upload handler
            ServletFileUpload upload = new ServletFileUpload(factory);
            Map<String, Object> map = new HashMap<>();
            
            try
            {
                // process uploads ..
                List fileItems = upload.parseRequest(request);
                Iterator iter = fileItems.iterator(); // 依次处理每个控件
                while (iter.hasNext())
                {
                    FileItem item = (FileItem)iter.next();// 忽略其他是文件域的所有表单信息
                    if (item.isFormField())
                    {
                        String fieldName = item.getFieldName();
                        // System.out.println(item.getFieldName()+"="+item.getString()); //获得表单数据
                        if (map.get(fieldName) != null)
                            ((List)map.get(fieldName)).add(item.getString());
                        else
                        {
                            List l = new ArrayList();
                            l.add(item.getString());
                            map.put(fieldName, l);
                        }
                    }
                    else
                    {
                        String fileName = item.getName();
                        // System.out.println(item.getName());
                        String path = request.getSession().getServletContext().getRealPath("/tmp") + "/"
                            + System.currentTimeMillis() + "_tmp" + fileName.substring(fileName.lastIndexOf("."));
                        // System.out.println(path);
                        File f = new File(path);
                        item.write(f);
                        map.put(item.getFieldName(), f);
                    }
                }
            }
            catch (FileUploadException e)
            {
                e.printStackTrace();
            }
            catch (Exception e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            
            Iterator<Entry<String, Object>> it = map.entrySet().iterator();
            while (it.hasNext())
            {
                Entry<String, Object> en = it.next();
                String key = en.getKey();
                Object value = en.getValue();
                if (value instanceof List)
                {
                    List list = (List)value;
                    map.put(key, (String[])list.toArray(new String[list.size()]));
                }
            }
            
            // System.out.println(map.size());
            return map;
        }
        
    }
}
