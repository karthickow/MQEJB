package com.student.ejb.tester;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Properties;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.rmi.PortableRemoteObject;

import com.student.bean.stateful.view.LibraryStatefulBeanRemote;
import com.student.bean.stateless.view.LibraryBeanRemote;

public class EJBTester {

	BufferedReader brConsoleReader = null;
	Properties props;
	InitialContext ctx;
	{
		props = new Properties();
		try {
			props.load(new FileInputStream("jndi.properties"));
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		try {
			ctx = new InitialContext(props);
		} catch (NamingException ex) {
			ex.printStackTrace();
		}
		brConsoleReader = new BufferedReader(new InputStreamReader(System.in));
	}

	public static void main(String[] args) {
		EJBTester ejbTester = new EJBTester();
		//ejbTester.testStatelessEjb();
		ejbTester.testStatefulEjb();
	}

	private void showGUI() {
		System.out.println("**********************");
		System.out.println("Welcome to Book Store");
		System.out.println("**********************");
		System.out.print("Options \n1. Add Book\n2. Exit \nEnter Choice: ");
	}

	private void testStatelessEjb() {
		try {
			int choice = 1;
			LibraryBeanRemote libraryBean = (LibraryBeanRemote) PortableRemoteObject.narrow(ctx.lookup("ejb/LibraryBean"), LibraryBeanRemote.class);
			
			while (choice != 2) {
				String bookName;
				showGUI();
				String strChoice = brConsoleReader.readLine();
				choice = Integer.parseInt(strChoice);

				if (choice == 1) {
					System.out.print("Enter book name: ");
					bookName = brConsoleReader.readLine();
					libraryBean.addBook(bookName);
				} else if (choice == 2) {
					break;
				}
			}

			List<String> booksList = libraryBean.getBooks();
			System.out.println("Book(s) entered so far: " + booksList.size());

			for (int i = 0; i < booksList.size(); ++i) {
				System.out.println((i + 1) + ". " + booksList.get(i));
			}

			LibraryBeanRemote libraryBean1 = (LibraryBeanRemote) PortableRemoteObject.narrow(ctx.lookup("ejb/LibraryBean"), LibraryBeanRemote.class);
			List<String> booksList1 = libraryBean1.getBooks();

			System.out.println("***Using second lookup to get library stateless object***");
			System.out.println("Book(s) entered so far: " + booksList1.size());
			
			for (int i = 0; i < booksList1.size(); ++i) {
				System.out.println((i + 1) + ". " + booksList1.get(i));
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				if (brConsoleReader != null) {
					brConsoleReader.close();
				}
			} catch (IOException ex) {
				System.out.println(ex.getMessage());
			}
		}
	}
	
	private void testStatefulEjb() {
		try {
			int choice = 1;

			LibraryStatefulBeanRemote libraryBean = (LibraryStatefulBeanRemote) PortableRemoteObject.narrow(ctx.lookup("ejb/LibraryStatefulBean"), LibraryStatefulBeanRemote.class);

			while (choice != 2) {
				String bookName;
				showGUI();
				String strChoice = brConsoleReader.readLine();
				choice = Integer.parseInt(strChoice);

				if (choice == 1) {
					System.out.print("Enter book name: ");
					bookName = brConsoleReader.readLine();
					libraryBean.addBook(bookName);
				} else if (choice == 2) {
					break;
				}
			}

			List<String> booksList = libraryBean.getBooks();
			System.out.println("Book(s) entered so far: " + booksList.size());

			for (int i = 0; i < booksList.size(); ++i) {
				System.out.println((i + 1) + ". " + booksList.get(i));
			}

			LibraryStatefulBeanRemote libraryBean1 = (LibraryStatefulBeanRemote) PortableRemoteObject.narrow(ctx.lookup("ejb/LibraryStatefulBean"),	LibraryStatefulBeanRemote.class);
			List<String> booksList1 = libraryBean1.getBooks();

			System.out.println("***Using second lookup to get library stateless object***");
			System.out.println("Book(s) entered so far: " + booksList1.size());
			
			for (int i = 0; i < booksList1.size(); ++i) {
				System.out.println((i + 1) + ". " + booksList1.get(i));
			}
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				if (brConsoleReader != null) {
					brConsoleReader.close();
				}
			} catch (IOException ex) {
				System.out.println(ex.getMessage());
			}
		}
	}
}
