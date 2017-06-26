/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.giex.api;

import br.com.giex.api.modelo.metadado;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;

import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSInputFile;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author jjunior
 */
public class InsertMongo {

    public Object insertArquivo(File file, String nomeArquivo, ArrayList<metadado> metadado, String banco, String colecao, String usuario, String senha) {
        try {
            FileInputStream fileInputStream = new FileInputStream(file);

            byte b[] = new byte[fileInputStream.available()];
            fileInputStream.read(b);

            DB db = Connection.getInstance().getClient(Connection.ENDERECOESCRITA, Connection.PORTAESCRITA, usuario, senha).getDB(banco);
            
            GridFS gridfs = new GridFS(db,colecao);

            GridFSInputFile gridFSInputFile = gridfs.createFile(file);

            /*
             * Criando metadado
             */
            if (metadado.size() > 0) {
                try {
                    BasicDBObject basicDBObject = new BasicDBObject();
                    for (int i = 0; i < metadado.size(); i++) {
                        basicDBObject.append(metadado.get(i).getChave(), metadado.get(i).getValor());
                    }
                    gridFSInputFile.setMetaData(basicDBObject);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            gridFSInputFile.save();

            fileInputStream.close();
            
            

            return gridFSInputFile.getId();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Object insertArquivo(byte[] file, String nomeArquivo, ArrayList<metadado> metadado, String banco, String colecao, String usuario, String senha) {
        try {
            DB db = Connection.getInstance().getClient(Connection.ENDERECOESCRITA, Connection.PORTAESCRITA, usuario, senha).getDB(banco);
            
            GridFS gridfs = new GridFS(db,colecao);
            GridFSInputFile gridFSInputFile = gridfs.createFile(file);

            /*
             * Criando metadado
             */
            if (metadado.size() > 0) {
                try {
                    BasicDBObject basicDBObject = new BasicDBObject();
                    for (int i = 0; i < metadado.size(); i++) {
                        basicDBObject.append(metadado.get(i).getChave(), metadado.get(i).getValor());
                    }
                    gridFSInputFile.setMetaData(basicDBObject);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            gridFSInputFile.save();

            return gridFSInputFile.getId();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
