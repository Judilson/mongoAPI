/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.giex.api;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jjunior
 */
public class Connection {

    public static final String ENDERECOESCRITA = "173.22.21.29";//"173.22.21.22";
    public static final String ENDERECOLEITURA1 = "173.22.21.38";//"173.22.21.24";
    public static final String ENDERECOLEITURA2 = "173.22.21.36";//"173.22.21.28";

    public static final int PORTAESCRITA = 27017;
    public static final int PORTALEITURA1 = 27017;//27018;
    public static final int PORTALEITURA2 = 27017;//27019;

    private static Connection uniqInstance;

    private MongoClient mongoClient;

    private Connection() {
    }

    public static synchronized Connection getInstance() {
        if (uniqInstance == null) {
            uniqInstance = new Connection();
        }
        return uniqInstance;
    }

    public MongoClient getClient(String endereco, int porta, String usuario, String senha) {
        if (mongoClient == null) {

            List<ServerAddress> seeds = new ArrayList<ServerAddress>();
            seeds.add(new ServerAddress(endereco,porta));
            List<MongoCredential> credentials = new ArrayList<MongoCredential>();
            credentials.add(
                    MongoCredential.createCredential(
                            usuario,
                            "giex",
                            senha.toCharArray()
                    )
            );
            mongoClient = new MongoClient(seeds,credentials);
        }
        return mongoClient;
    }

//    public static synchronized MongoClient conexao(String endereco, int porta, String usuario, String senha) {
//
//        
//        
//        mongoClient = new MongoClient(endereco, porta);
//
//        return mongoClient;
//    }
}
