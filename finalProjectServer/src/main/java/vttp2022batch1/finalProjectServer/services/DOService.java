package vttp2022batch1.finalProjectServer.services;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;

import vttp2022batch1.finalProjectServer.models.Registration;

@Service
public class DOService {

    @Autowired
    private AmazonS3 s3;

    public boolean postS3upload(Registration reg){

                // My private metadata
                Map<String, String> myData = new HashMap<>();
                
                myData.put("createdOn", (new Date()).toString());
        
                //MetaData for the Object
                ObjectMetadata metadata = new ObjectMetadata();
                metadata.setContentType(reg.getProfilePic().getContentType());
                metadata.setContentLength(reg.getProfilePic().getSize());
                metadata.setUserMetadata(myData);

                    try {
                        PutObjectRequest putReq = new PutObjectRequest("saravttp", "users/%s".formatted(reg.getUsername()), 
                            reg.getProfilePic().getInputStream(), metadata);
                        putReq = putReq.withCannedAcl(CannedAccessControlList.PublicRead);
                        PutObjectResult result = s3.putObject(putReq);
                        
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    return true;                 
                }

    }



   
    


    

