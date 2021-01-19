package com.queue.mydemo.controller;

import java.util.EnumSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.microsoft.azure.storage.*;
import com.microsoft.azure.storage.queue.*;


@RestController
public class MyRestController {

	@Autowired
	public CloudQueueClient queueClient;


	
	@RequestMapping(value ="/getMessage",method= RequestMethod.GET)
    public String getMsg(){
		String message = "";
		try
		{

		    CloudQueue queue = queueClient.getQueueReference("firstqueue");
		    // 取得下訊息.
		    CloudQueueMessage peekedMessage = queue.peekMessage();

		    if (peekedMessage != null)
		    {
		    	message = peekedMessage.getMessageContentAsString();
		   }
		}
		catch (Exception e)
		{
		    e.printStackTrace();
		}	
		return "Get Message==> "+message;
	}
	
	
	@RequestMapping(value ="/add/{msg}",method= RequestMethod.GET)
    public String add(@PathVariable("msg") String msg){
		try
		{

		    CloudQueue queue = queueClient.getQueueReference("firstqueue");
		    CloudQueueMessage message = new CloudQueueMessage(msg);
		    queue.addMessage(message);
		}
		catch (Exception e)
		{
		    // Output the stack trace.
		    e.printStackTrace();
		}			
        return "It is work!";
    }
	
	@RequestMapping(value ="/update/{orimsg}/{msg}",method= RequestMethod.GET)
    public String update(@PathVariable("orimsg") String orimsg,@PathVariable("msg") String msg){
		try
		{

		    CloudQueue queue = queueClient.getQueueReference("firstqueue");
		    // 設定訊息可抓取數量
		    final int MAX_NUMBER_OF_MESSAGES_TO_PEEK =10;

		    // 迴圈抓取訊息
		    for (CloudQueueMessage message : queue.retrieveMessages(MAX_NUMBER_OF_MESSAGES_TO_PEEK,1,null,null))
		    {
		        // 抓取要更新的訊息.
		        if (message.getMessageContentAsString().equals(orimsg))
		        {
		            
		            message.setMessageContent(msg);
		            // 設定30秒後可以看見更新的訊息.
		            EnumSet<MessageUpdateFields> updateFields =
		                EnumSet.of(MessageUpdateFields.CONTENT,
		                MessageUpdateFields.VISIBILITY);
		            // 更新訊息.
		            queue.updateMessage(message, 30, updateFields, null, null);
		            break;
		        }
		    }
		}
		catch (Exception e)
		{
		    e.printStackTrace();
		}		
		
		return "Update OK!";
		
	}
	

}
