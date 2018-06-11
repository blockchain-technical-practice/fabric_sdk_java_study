/**
 *  fabric-sdk-test
 */
package com.onechain.fabric.test;


import static java.lang.String.format;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.spec.InvalidKeySpecException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Properties;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.TimeUnit;

import org.apache.commons.codec.binary.Hex;
import org.hyperledger.fabric.sdk.*;
import org.hyperledger.fabric.sdk.ChaincodeResponse.Status;
import org.hyperledger.fabric.sdk.exception.CryptoException;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.exception.ProposalException;
import org.hyperledger.fabric.sdk.exception.TransactionException;
import org.hyperledger.fabric.sdk.security.CryptoSuite;
import org.hyperledger.fabric.sdk.testutils.TestConfig;
import org.hyperledger.fabric.sdkintegration.SampleOrg;
import org.hyperledger.fabric.sdkintegration.SampleStore;
import org.hyperledger.fabric.sdkintegration.SampleUser;
import org.hyperledger.fabric_ca.sdk.HFCAClient;
import org.hyperledger.fabric_ca.sdk.exception.EnrollmentException;

/**
 * @author fengxiang
 *
 */
public class TestMain {

	
	private static final String CHANNEL_NAME = "channel1";
    static HFClient hfclient = null;
    private static TestConfigHelper configHelper = new TestConfigHelper();
    private static Collection<SampleOrg> testSampleOrgs;
    private static final TestConfig testConfig = TestConfig.getConfig();


	static Channel testchannel = null;
	static ChaincodeResponse deployResponse = null;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		 //peerTest();
		//peerTest();
		//fabric_ca();
		//System.setProperty("org.hyperledger.fabric.sdk.configuration", "/project/javaworkspace/fabric-sdk-java-sample/src/org/hyperledger/fabric/sdk/configuration/config.properties");
		fabric_local();
		
	
	}
	
	/**
	 * 
	 * 账本相关的操作演示(不通过CA获取相关文件的演示)
	 * 
	 */
	public static void fabric_local(){
		
		
        try {
        	
        	
        	//System.out.println(System.getProperty("org.hyperledger.fabric.sdk.configuration", "config.properties"));
        	
//        	Properties sdkProperties = new Properties();
//        	sdkProperties.put("org.hyperledger.fabric.sdk.orderer.ordererWaitTimeMilliSecs", "4000000");
//        	
        	
        	HFClient hfclient = HFClient.createNewInstance();
        	
        	CryptoSuite cryptosuite= CryptoSuite.Factory.getCryptoSuite();
        	
        	//cryptosuite.setProperties(sdkProperties);
        	//cryptosuite.
        	
        	hfclient.setCryptoSuite(cryptosuite);
        	
			
			  
			File sampleStoreFile = new File(System.getProperty("user.home") + "/HFCSampletest.properties");
			if (sampleStoreFile.exists()) { // For testing start fresh
			    sampleStoreFile.delete();
			}
			SampleStore sampleStore = new SampleStore(sampleStoreFile);
			



			/*SampleUser admin = sampleStore.getMember(
					"Admin",
					"org1",
					"Org1MSP", 
					findFileSk("/project/opt_fabric/fabricconfig/crypto-config/peerOrganizations/org1.robertfabrictest.com/users/Admin@org1.robertfabrictest.com/msp/keystore"),
					 new File("/project/opt_fabric/fabricconfig/crypto-config/peerOrganizations/org1.robertfabrictest.com/users/Admin@org1.robertfabrictest.com/msp/signcerts/Admin@org1.robertfabrictest.com-cert.pem"));
*/

			//利用客户单发起新的交易
			SampleUser admin = sampleStore.getMember(
					"Admin",
					"org1",
					"Org1MSP",
					findFileSk("/project/fabric_resart/config_demo/org1/186/fabric-user/msp/keystore"),
					new File("/project/fabric_resart/config_demo/org1/186/fabric-user/msp/signcerts/cert.pem"));





			hfclient.setUserContext(admin);




			testchannel = hfclient.newChannel("roberttestchannel12");
						
			
			Peer peer = hfclient.newPeer( "peer0", "grpc://192.168.23.212:7051");
			testchannel.addPeer( peer );
			
			Peer peer188 = hfclient.newPeer( "peer0", "grpc://172.16.10.188:7051");
			testchannel.addPeer( peer188 );
			
			
			//如果采用加密的方式，建议采用这样的访问写法  
//			Properties peerproperties = new Properties();
//			peerproperties.put("pemFile", "/project/opt_fabric/fabricconfig/crypto-config/peerOrganizations/org1.robertfabrictest.com/users/Admin@org1.robertfabrictest.com/tls/server.crt");
//			Peer peer = hfclient.newPeer( "peer0", "grpc://192.168.23.212:7051",peerproperties);
//			
			Orderer order = hfclient.newOrderer( "orderer" , "grpc://192.168.23.212:7050" );


			/*Properties orderpeerproperties = new Properties();
			orderpeerproperties.put("pemFile", "/project/opt_fabric/fabricconfig/crypto-config/ordererOrganizations/robertfabrictest.com/orderers/orderer.robertfabrictest.com/tls/ca.crt");
			Orderer order = hfclient.newOrderer( "orderer" , "grpc://192.168.23.212:7050",orderpeerproperties );*/

			
			testchannel.addOrderer(order);
			
		    
			
			testchannel.initialize();

			
		
			
			//根据区块编号获取区块详细信息
//			BlockInfo blockinfo= testchannel.queryBlockByNumber(80);
//			ByteString blockhash = blockinfo.getBlock().getHeader().getPreviousHash();
//			String blockhashstr = Hex.encodeHexString(blockinfo.getPreviousHash());
//			System.out.println(blockhashstr);
			
			//根据区块链HASH获取区块详细信息
//			BlockInfo blockinfo1 = testchannel.queryBlockByHash(Hex.decodeHex(blockhashstr.toCharArray()));
//			System.out.println(blockinfo1.getBlock().toBuilder().toString());
			
			//获取当前peer服务器的信息
//			BlockchainInfo blockchianinfo = testchannel.queryBlockchainInfo(peer);
//			System.out.println(blockchianinfo.getHeight()+" ");
			
			//Map<K, V> blockchianinfo.getBlockchainInfo().getAllFields();
			
			
			//查询当前Peer服务器加入的channel
//			Set<String> peerchannels =  hfclient.queryChannels(peer);
//			
//			for (String string : peerchannels) {
//				System.out.println(string);
//			}
			
			
			//查询已经install的chaincode
//			List<ChaincodeInfo> installchaincodes =  hfclient.queryInstalledChaincodes(peer);
//			
//			for (ChaincodeInfo chaincodeInfo : installchaincodes) {
//				System.out.println(chaincodeInfo.getPath());
//			}
			
			//查询已经实例化的Chaincode
//			List<ChaincodeInfo> instancechaincodes = testchannel.queryInstantiatedChaincodes(peer);
//			for (ChaincodeInfo chaincodeInfo : instancechaincodes) {
//				System.out.println(chaincodeInfo.getPath());
//			}
			
			//根据交易编号获取交易详细信息
			TransactionInfo transactionInfo =  testchannel.queryTransactionByID("8e3b4c2158beb96bb5b4990289b538046204a7c07744ed877138713269042b9c");
			System.out.println( transactionInfo.getTransactionID() );


			//根据交易编号获取区块的详细信息
			BlockInfo blockinfo = testchannel.queryBlockByTransactionID(peer,"8e3b4c2158beb96bb5b4990289b538046204a7c07744ed877138713269042b9c");
			String blockhashstr = Hex.encodeHexString(blockinfo.getPreviousHash());
			System.out.println(blockhashstr);


			/// ============   管理类的chaincode =========


			
			 ///// ====== 调用chaincode 
			
//			ChaincodeID chaincodeID = ChaincodeID.newBuilder().setName("sampledemo5_9").build();	
//			
//			QueryByChaincodeRequest queryByChaincodeRequest = hfclient.newQueryProposalRequest();
//			queryByChaincodeRequest.setArgs(new String[]{"get","akey","333333333 hahaha lst key dddddd this is last version"});
//			queryByChaincodeRequest.setFcn("invoke");
//			queryByChaincodeRequest.setChaincodeID(chaincodeID);
//			
//			Collection<ProposalResponse> queryProposals;
//			queryProposals = testchannel.queryByChaincode(queryByChaincodeRequest);
//			 
//			for (ProposalResponse proposalResponse : queryProposals) {
//	            if (!proposalResponse.isVerified() || proposalResponse.getStatus() != Status.SUCCESS) {
//	                System.out.println("Failed query proposal from peer " + proposalResponse.getPeer().getName() + " status: " + proposalResponse.getStatus() +
//	                        ". Messages: " + proposalResponse.getMessage()
//	                        + ". Was verified : " + proposalResponse.isVerified());
//	            } else {
//	                String payload = proposalResponse.getProposalResponse().getResponse().getPayload().toStringUtf8();
//	                out("Query payload of b from peer %s returned %s", proposalResponse.getPeer().getName(), payload);
//	               
//	            }
//	        }
//			
			
			
			//// ======================== 发起交易  ========================
			
//			ChaincodeID chaincodeID = ChaincodeID.newBuilder().setName("sampledemo5_9").build();
//			sendTranstion(hfclient, testchannel, chaincodeID, admin).thenApply(transactionEvent -> {
//				
//				String tranid = transactionEvent.getTransactionID();
//				System.out.println(" ====  "  +tranid);
//				
//				return null;
//				
//			  }).exceptionally( e -> {
//				  return null;
//			  });
			 
		    //// ======================== 发起交易并且通过背书完成交易 ========================
			
			
			/*ChaincodeID chaincodeID = ChaincodeID.newBuilder().setName("cc_endfinlshed").build();
			sendTranstion(hfclient, testchannel, chaincodeID, admin).thenApply(transactionEvent -> {
				
				String tranid = transactionEvent.getTransactionID();
				System.out.println(" ====  "  +tranid);
				
				return null;
				
			  }).exceptionally( e -> {
				  return null;
			  });
			*/
			
			
			
			
		} catch (CryptoException | InvalidArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransactionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchProviderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 

		
		
	}
	
	
	
	 
	private static CompletableFuture<BlockEvent.TransactionEvent> sendTranstion(HFClient client, Channel channel, ChaincodeID chaincodeID, User user) {
		
		  try {
					//ChaincodeID chaincodeID = ChaincodeID.newBuilder().setName("sampledemo5_9").build();
					Collection<ProposalResponse> successful = new LinkedList<>();
					
					TransactionProposalRequest transactionProposalRequest = client.newTransactionProposalRequest();
					transactionProposalRequest.setChaincodeID(chaincodeID);
					transactionProposalRequest.setFcn("invoke");
					transactionProposalRequest.setArgs(new String[] {"a","b","1"});
					transactionProposalRequest.setProposalWaitTime(300000);
					transactionProposalRequest.setUserContext(user);
					
					Collection<ProposalResponse> invokePropResp = channel.sendTransactionProposal(transactionProposalRequest);
					
					for (ProposalResponse response : invokePropResp) {
			            if (response.getStatus() == Status.SUCCESS) {
			                out("Successful transaction proposal response Txid: %s from peer %s", response.getTransactionID(), response.getPeer().getName());
			                successful.add(response);
			            } else {
			                out("fiale %s ", "dddd");
			            }
			        }
					
					return  channel.sendTransaction(successful,user);
		
	        } catch (Exception e) {

	        	throw new CompletionException(e);

	        }
		
	}
	
	
	
	
	
	/////////////////////
	
	
	/**
	 * 
	 * 账本相关的操作演示
	 * 
	 */
	public static void fabric_ca(){
		
		
        try {
        	
        	HFClient hfclient = HFClient.createNewInstance();
        	
        	CryptoSuite cryptosuite= CryptoSuite.Factory.getCryptoSuite();
        	
        	//cryptosuite.init();
        	//cryptosuite.
        	
			hfclient.setCryptoSuite(cryptosuite);
			HFCAClient caclient = HFCAClient.createNewInstance( "http://192.168.23.212:7054",null  );



			caclient.setCryptoSuite(cryptosuite);
			Enrollment enrollment = caclient.enroll(  "user88"  ,  "peer2wd"  );
			
			  
			File sampleStoreFile = new File(System.getProperty("user.home") + "/HFCSampletest.properties");
			if (sampleStoreFile.exists()) { // For testing start fresh
			    sampleStoreFile.delete();
			}
			SampleStore sampleStore = new SampleStore(sampleStoreFile);
			SampleUser admin = sampleStore.getMember("admin","org1");
			admin.setMspId("Org1MSP");
			
			//admin.setAffiliation(affiliation)


			admin.setEnrollment(enrollment);
			
			hfclient.setUserContext(admin);
			
			
			
			testchannel = hfclient.newChannel("roberttestchannel12");
						
			Peer peer = hfclient.newPeer( "peertest", "grpc://192.168.23.212:7051");
			testchannel.addPeer( peer );

			Peer peer188 = hfclient.newPeer( "peer0", "grpc://172.16.10.188:7051");
			testchannel.addPeer( peer188 );


			Orderer order = hfclient.newOrderer("order", "grpc://192.168.23.212:7050");
			testchannel.addOrderer(order);



			testchannel.initialize();


			BlockchainInfo blockchianinfo = testchannel.queryBlockchainInfo(peer);
			System.out.println(blockchianinfo.getHeight()+" ");

			BlockInfo blockinfo= testchannel.queryBlockByNumber(2);
			System.out.println(blockinfo.getBlock().getMetadata().getMetadata(0).toString());


			ChaincodeID chaincodeID = ChaincodeID.newBuilder().setName("cc_endfinlshed").build();
			sendTranstion(hfclient, testchannel, chaincodeID, admin).thenApply(transactionEvent -> {

				String tranid = transactionEvent.getTransactionID();
				System.out.println(" ====  "  +tranid);

				return null;

			}).exceptionally( e -> {
				return null;
			});


		} catch (CryptoException | InvalidArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (EnrollmentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (org.hyperledger.fabric_ca.sdk.exception.InvalidArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ProposalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransactionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 

		
		
	}
	
	
	
	
	public static void testPeer(){
		
		System.out.println(System.getProperty("user.home"));
		
		
	}
	
	
	public static  void testclient(){
		
		
	}
	
	
	
	public static HFClient newInstance() throws Exception {


        File tempFile = File.createTempFile("teststore", "properties");
        tempFile.deleteOnExit();

        File sampleStoreFile = new File(System.getProperty("user.home") + "/test.properties");
        
        if (sampleStoreFile.exists()) { //For testing start fresh
            sampleStoreFile.delete();
        }
        
        final SampleStore sampleStore = new SampleStore(sampleStoreFile);

        //src/test/fixture/sdkintegration/e2e-2Orgs/channel/crypto-config/peerOrganizations/org1.example.com/users/Admin@org1.example.com/msp/keystore/

        //SampleUser someTestUSER = sampleStore.getMember("someTestUSER", "someTestORG");
        SampleUser someTestUSER = sampleStore.getMember("someTestUSER", "someTestORG", "mspid",
                findFileSk("src/test/fixture/sdkintegration/e2e-2Orgs/channel/crypto-config/peerOrganizations/org1.example.com/users/Admin@org1.example.com/msp/keystore"),
                new File("src/test/fixture/sdkintegration/e2e-2Orgs/channel/crypto-config/peerOrganizations/org1.example.com/users/Admin@org1.example.com/msp/signcerts/Admin@org1.example.com-cert.pem"));
        someTestUSER.setMspId("testMSPID?");

        HFClient hfclient = HFClient.createNewInstance();
        hfclient.setCryptoSuite(CryptoSuite.Factory.getCryptoSuite());


        hfclient.setUserContext(someTestUSER);

        return hfclient;

    } 

	
	
	
	/////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	private static File findFileSk(String directorys) {

        File directory = new File(directorys);

        File[] matches = directory.listFiles((dir, name) -> name.endsWith("_sk"));

        if (null == matches) {
            throw new RuntimeException(format("Matches returned null does %s directory exist?", directory.getAbsoluteFile().getName()));
        }

        if (matches.length != 1) {
            throw new RuntimeException(format("Expected in %s only 1 sk file but found %d", directory.getAbsoluteFile().getName(), matches.length));
        }

        return matches[0];

    }
	
	
	
	/////////////////////////    ======================== PEER  TEST =======================   ///////////////////////////
	

	
    
	public static void peerTest(){
		
		try {
			
			testSampleOrgs = testConfig.getIntegrationTestsSampleOrgs();
			
			configHelper.clearConfig();
	        configHelper.customizeConfig();

	        testSampleOrgs = testConfig.getIntegrationTestsSampleOrgs();
	        //Set up hfca for each sample org

	      
	        HFClient hfclient1 = newInstance();
	        
	        
	        for (SampleOrg sampleOrg : testSampleOrgs) {
	        	
	            String caName = sampleOrg.getCAName(); //Try one of each name and no name.
	            if (caName != null && !caName.isEmpty()) {
	                sampleOrg.setCAClient(HFCAClient.createNewInstance(caName, sampleOrg.getCALocation(), sampleOrg.getCAProperties()));
	            } else {
	                sampleOrg.setCAClient(HFCAClient.createNewInstance(sampleOrg.getCALocation(), sampleOrg.getCAProperties()));
	            }
	            
	        }
			
			
	        //hfclient1.setCryptoSuite(CryptoSuite.Factory.getCryptoSuite());

	        
			Peer peer = hfclient1.newPeer( "peertest", "grpc://192.168.23.212:7051");
			
			System.out.println(peer.getName());
			
			//testChain = new Channel( "chain1" ,null );
			//testChain = new Channel
			
			//testChain = new Channel( null , null );
			//testchannel = hfclient1.getChannel("mychannel");
			
			testchannel = hfclient1.newChannel("mychannel");
			testchannel.initialize();
			
			testchannel.addPeer( peer );
			
			Properties ordererProperties = new Properties();

			//example of setting keepAlive to avoid timeouts on inactive http2 connections.
			// Under 5 minutes would require changes to server side to accept faster ping rates.
			ordererProperties.put("grpc.NettyChannelBuilderOption.keepAliveTime", new Object[] {5L, TimeUnit.MINUTES});
			ordererProperties.put("grpc.NettyChannelBuilderOption.keepAliveTimeout", new Object[] {8L, TimeUnit.SECONDS});


			Orderer order = hfclient1.newOrderer("order", "grpc://192.168.23.212:7050");

			
			testchannel.addOrderer(order);
			
			
			BlockInfo blockinfo= testchannel.queryBlockByNumber(2);
		
			
          
			String moveAmount = "10";
			
			User user = null;
			
			//testchannel.queryByChaincode(queryByChaincodeRequest)
//			try {
//	            Collection<ProposalResponse> successful = new LinkedList<>();
//	            Collection<ProposalResponse> failed = new LinkedList<>();
//
//	            
//	            ChaincodeID chaincodeID = ChaincodeID.newBuilder().setName("dddd")
//	                    .setVersion("11")
//	                    .setPath("dddddd").build();
//	            
//	            ///////////////
//	            /// Send transaction proposal to all peers
//	            TransactionProposalRequest transactionProposalRequest = hfclient1.newTransactionProposalRequest();
//	            transactionProposalRequest.setChaincodeID(chaincodeID);
//	            transactionProposalRequest.setFcn("invoke");
//	            transactionProposalRequest.setArgs(new String[] {"move", "a", "b", "11"});
//	            transactionProposalRequest.setProposalWaitTime(5000);
//	            if (user != null) { // specific user use that
//	                transactionProposalRequest.setUserContext(user);
//	            }
//	            out("sending transaction proposal to all peers with arguments: move(a,b,%s)", moveAmount);
//
//	            Collection<ProposalResponse> invokePropResp = testchannel.sendTransactionProposal(transactionProposalRequest);
//	            for (ProposalResponse response : invokePropResp) {
//	                if (response.getStatus() == Status.SUCCESS) {
//	                	out("Successful transaction proposal response Txid: %s from peer %s", response.getTransactionID(), response.getPeer().getName());
//	                    successful.add(response);
//	                } else {
//	                    failed.add(response);
//	                }
//	            }
//
//	            out("Received %d transaction proposal responses. Successful+verified: %d . Failed: %d",
//	                    invokePropResp.size(), successful.size(), failed.size());
//	            if (failed.size() > 0) {
//	                ProposalResponse firstTransactionProposalResponse = failed.iterator().next();
//
//	                throw new ProposalException(format("Not enough endorsers for invoke(move a,b,%s):%d endorser error:%s. Was verified:%b",
//	                        moveAmount, firstTransactionProposalResponse.getStatus().getStatus(), firstTransactionProposalResponse.getMessage(), firstTransactionProposalResponse.isVerified()));
//
//	            }
//	            System.out.println("Successfully received transaction proposal responses.");
//
//	            ////////////////////////////
//	            // Send transaction to orderer
//	            out("Sending chaincode transaction(move a,b,%s) to orderer.", moveAmount);
//	            if (user != null) {
//	                 testchannel.sendTransaction(successful, user);
//	            }
//	             testchannel.sendTransaction(successful);
//	        } catch (Exception e) {
//
//	            e.printStackTrace();
//
//	        }

			
			
			
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
		
		
	}
	
	
	static void out(String format, Object... args) {

        System.err.flush();
        System.out.flush();

        System.out.println(format(format, args));
        System.err.flush();
        System.out.flush();

    }
	

}


