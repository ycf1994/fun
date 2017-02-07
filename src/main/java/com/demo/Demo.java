package com.demo;

import java.io.File;

public class Demo {
	public static void main(String[] args) {
		File file=new File("d:\\1.docx");
		System.out.println(System.currentTimeMillis()-file.lastModified());
	}
}
