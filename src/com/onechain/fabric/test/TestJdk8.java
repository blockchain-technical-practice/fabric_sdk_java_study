/**
 * 
 */
package com.onechain.fabric.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author fengxiang
 *
 */
public class TestJdk8 {

	
	private static List<String> arrList = Arrays.asList("mark","jike","tom","kate","rober","aliex","phaha");
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
//		stroe();
//		
//		for (String string : arrList) {
//			System.out.println(string);
//		}
		
		//System.setProperty("org.hyperledger.fabric.sdk.configuration", "/project/javaworkspace/fabric-sdk-java-sample/src/org/hyperledger/fabric/sdk/configuration");
		


		//testfile();


		for(int i = 0 ; i < 1000000000 ; i++){

			String hashstr = SHA256("study blockchain!"+i);

			if( hashstr.indexOf("0000")==0 )
				System.out.println( hashstr + "  "+i );


		}
		
        
	}



	public static String SHA256(final String strText) {
		return SHA(strText, "SHA-256");
	}


	private static String SHA(final String strText, final String strType)
	{
		// 返回值
		String strResult = null;

		// 是否是有效字符串
		if (strText != null && strText.length() > 0)
		{
			try
			{
				// SHA 加密开始
				// 创建加密对象 并傳入加密類型
				MessageDigest messageDigest = MessageDigest.getInstance(strType);
				// 传入要加密的字符串
				messageDigest.update(strText.getBytes());
				// 得到 byte 類型结果
				byte byteBuffer[] = messageDigest.digest();

				// 將 byte 轉換爲 string
				StringBuffer strHexString = new StringBuffer();
				// 遍歷 byte buffer
				for (int i = 0; i < byteBuffer.length; i++)
				{
					String hex = Integer.toHexString(0xff & byteBuffer[i]);
					if (hex.length() == 1)
					{
						strHexString.append('0');
					}
					strHexString.append(hex);
				}
				// 得到返回結果
				strResult = strHexString.toString();
			}
			catch (NoSuchAlgorithmException e)
			{
				e.printStackTrace();
			}
		}

		return strResult;
	}


	public  static void stroe(){
		
		Collections.sort(arrList, (String a,String b)->{ return a.compareTo(b);});
	}

	
	
	public static void testfile(){
		
		Properties sdkProperties = new Properties();
		FileInputStream configProps;
		
		String filepath = System.getProperty("org.hyperledger.fabric.sdk.configuration", "config.properties");
		System.out.println( filepath );
		File loadFile;
		loadFile = new File(filepath).getAbsoluteFile();
		
		try {
			
			
			configProps = new FileInputStream(loadFile);
			sdkProperties.load(configProps);
			
			System.out.println( "ddd" );
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
	}
	
	
}
