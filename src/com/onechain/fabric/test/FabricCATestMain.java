/**
 *
 */
package com.onechain.fabric.test;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.net.MalformedURLException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.CertificateParsingException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

import org.hyperledger.fabric.sdk.Enrollment;
import org.hyperledger.fabric.sdk.exception.CryptoException;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.security.CryptoSuite;
import org.hyperledger.fabric.sdk.testutils.TestConfig;
import org.hyperledger.fabric.sdkintegration.SampleStore;
import org.hyperledger.fabric.sdkintegration.SampleUser;
import org.hyperledger.fabric_ca.sdk.Attribute;
import org.hyperledger.fabric_ca.sdk.EnrollmentRequest;
import org.hyperledger.fabric_ca.sdk.HFCAClient;
//import org.hyperledger.fabric_ca.sdk.MockHFCAClient;
import org.hyperledger.fabric_ca.sdk.RegistrationRequest;
import org.hyperledger.fabric_ca.sdk.exception.EnrollmentException;
import org.hyperledger.fabric_ca.sdk.exception.RevocationException;
import org.hyperledger.fabric_ca.sdk.helper.Config;

/**
 * @author fengxiang
 *
 */
public class FabricCATestMain {

	
	
	private static final String TEST_ADMIN_NAME = "admin";
    private static final String TEST_ADMIN_PW = "adminpw";
    private static final String TEST_ADMIN_ORG = "org1";
    private static final String TEST_USER1_ORG = "Org2";
    private static final String TEST_USER1_AFFILIATION = "org1.department11";
    private static final String TEST_WITH_INTEGRATION_ORG = "peerOrg1";

    private static SampleStore sampleStore;
    private static HFCAClient client;
    private static SampleUser admin;

    private static CryptoSuite crypto;

    // Keeps track of how many test users we've created
    private static int userCount = 0;

    // Common prefix for all test users (the suffix will be the current user count)
    // Note that we include the time value so that these tests can be executed repeatedly
    // without needing to restart the CA (because you cannot register a username more than once!)
    private static String userNamePrefix = "user" + (System.currentTimeMillis() / 1000) + "_";

    private static TestConfig testConfig = TestConfig.getConfig();

    private static Properties caProperties = new Properties();
    
    
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		 
			try {
				getCAClient();
			} catch (CryptoException e) {
				
				e.printStackTrace();
			} catch (InvalidArgumentException e) {
				
				e.printStackTrace();
			} catch (MalformedURLException e) {
				
				e.printStackTrace();
			} catch (EnrollmentException e) {
				
				e.printStackTrace();
			} catch (org.hyperledger.fabric_ca.sdk.exception.InvalidArgumentException e) {
				
				e.printStackTrace();
			} catch(Exception ex){
				ex.printStackTrace();
			}
		

	}
	
	
	public static void getCAClient() throws Exception{
		
		 //out("\n\n\nRUNNING: HFCAClientEnrollIT.\n");
			crypto = CryptoSuite.Factory.getCryptoSuite();
		    //crypto.init();
		     
		     
		     
			File sampleStoreFile = new File(System.getProperty("user.home") + "/HFCSampletest.properties");
			if (sampleStoreFile.exists()) { // For testing start fresh
			    sampleStoreFile.delete();
			}
			sampleStore = new SampleStore(sampleStoreFile);
			sampleStoreFile.deleteOnExit();
			
			caProperties = testConfig.getIntegrationTestsSampleOrg( TEST_WITH_INTEGRATION_ORG ).getCAProperties();
			
			client = HFCAClient.createNewInstance( "http://localhost:7054",caProperties  );
			
			client.setCryptoSuite(crypto);
			
			// SampleUser can be any implementation that implements org.hyperledger.fabric.sdk.User Interface
			admin = sampleStore.getMember(TEST_ADMIN_NAME, TEST_ADMIN_ORG);
			
			if (!admin.isEnrolled()) { // Preregistered admin only needs to be enrolled with Fabric CA.
				
				
				String name = admin.getName();
				Enrollment enrollment = client.enroll(  name  ,  TEST_ADMIN_PW );
			    admin.setEnrollment( enrollment  );
			    
			    
			    /////////////////////// === regsit user ===== ///////////////////////////////
			    
			    RegistrationRequest regreq = new RegistrationRequest("fx19800215", "org1.department1");
		        
	            
		        SampleUser reguser = sampleStore.getMember("fx19800215", "org1");
		        reguser.setEnrollment(enrollment);
	        
		        String regResult = client.register( regreq , admin );
//		        
		        System.out.println(regResult);
		        
		        
//		        Enrollment enrollment1 = client.enroll(  "fx19800215"  ,  "pptAFMVHATIR" );
//		        
//		        
//		        System.out.println(enrollment1.getCert());
//		        System.out.println( "============  测试  =========  " );
//		        System.out.println(enrollment1.getKey().toString());
		        

			}
			
				     
	     
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
