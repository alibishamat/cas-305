package com.snhu.sslserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@SpringBootApplication
public class SslServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SslServerApplication.class, args);
	}

}
//FIXME: Add route to enable check sum return of static data example:  String data = "Hello World Check Sum!";

@RestController
class ServerController {

    // Function to convert bytes to a hexadecimal string
    private String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

    // RESTful endpoint to generate and return the hash
    @RequestMapping("/hash")
    public String myHash() {
        String data = "Hello World Check Sum! By Alibi Shamat"; // Unique data string including your name
        String checksum = "";

        try {
            // Create a MessageDigest instance with the desired algorithm
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            
            // Generate the hash value in byte format
            byte[] hashBytes = messageDigest.digest(data.getBytes());
            
            // Convert the byte hash value to hexadecimal
            checksum = bytesToHex(hashBytes);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "Error generating hash: " + e.getMessage();
        }

        // Return the original data and its checksum
        return "<p>Data: " + data + "</p><p>Checksum (SHA-256): " + checksum + "</p>";
    }
}