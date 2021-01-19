package com.queue.mydemo.config;

import java.net.URISyntaxException;
import java.security.InvalidKeyException;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.StorageException;
import com.microsoft.azure.storage.queue.CloudQueue;
import com.microsoft.azure.storage.queue.CloudQueueClient;

@Configuration
@EnableAutoConfiguration
public class Config {
	
	@Bean
	public CloudQueueClient myQueue() throws Exception
	{
		String storageConnectionString =
			    "DefaultEndpointsProtocol=https;" +
			    "AccountName=myqueuetest;" +
			    "AccountKey=sZ0ZM0OVWEqG6CcPXFbBU/ZsDAScD9E5zQ2ytCXoTKCHETLXFrAnrmfxxvoZtYHnmKeOoc3eWGJcxYNePMwRXQ==";		
	    CloudStorageAccount storageAccount = CloudStorageAccount.parse(storageConnectionString);
	    CloudQueueClient queueClient = storageAccount.createCloudQueueClient();
	   
	    
	    return queueClient;
	}

}
