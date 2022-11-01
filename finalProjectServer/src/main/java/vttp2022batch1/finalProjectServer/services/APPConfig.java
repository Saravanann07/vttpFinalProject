package vttp2022batch1.finalProjectServer.services;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

@Configuration
public class APPConfig {

    private String accessKey = "DO00HRWEGZDA9EU3XZ2U";
    private String secretKey = "byWestPxPBOpIS1i3DT2w1uMmH98aAsFAkMPA7H35wo";

    @Bean
    public AmazonS3 createS3Client(){
    EndpointConfiguration epConfig = new EndpointConfiguration("sgp1.digitaloceanspaces.com", "sgp1");
    BasicAWSCredentials cred = new BasicAWSCredentials(
        accessKey, secretKey);
   
    return AmazonS3ClientBuilder.standard()
            .withEndpointConfiguration(epConfig)
            .withCredentials(new AWSStaticCredentialsProvider(cred))
            .build();
    }

}



