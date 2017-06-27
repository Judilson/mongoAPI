/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.giex.api;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bson.types.ObjectId;

/**
 *
 * @author jjunior
 */
public class RetrieveMongo {

    public File retrieveFile(String objectID, String banco, String colecao, String usuario, String senha) {
        File temp = null;
        try {
            temp = File.createTempFile("tempfile", ".tmp");
        } catch (IOException ex) {
            Logger.getLogger(RetrieveMongo.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {

            BasicDBObject basicDBObject = new BasicDBObject();
            basicDBObject.put("_id", new ObjectId(objectID));

            DB db = Connection.getInstance().getClient(Connection.ENDERECOLEITURA2, Connection.PORTALEITURA2, usuario, senha).getDB(banco);

            GridFS gridFS = new GridFS(db, colecao);
            GridFSDBFile gridFSDBFile = gridFS.findOne(basicDBObject);

            gridFSDBFile.writeTo(temp);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return temp;
    }

    public File retrieveFile(String key, Object value, String banco, String colecao, String usuario, String senha) {
        File temp = null;
        try {
            temp = File.createTempFile("tempfile", ".tmp");
        } catch (IOException ex) {
            Logger.getLogger(RetrieveMongo.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {

            BasicDBObject basicDBObject = new BasicDBObject();
            basicDBObject.put(key, value);

            DB db = Connection.getInstance().getClient(Connection.ENDERECOLEITURA2, Connection.PORTALEITURA2, usuario, senha).getDB(banco);

            GridFS gridFS = new GridFS(db, colecao);
            GridFSDBFile gridFSDBFile = gridFS.findOne(basicDBObject);

            gridFSDBFile.writeTo(temp);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return temp;
    }

    public byte[] retrieveByte(String objectID, String banco, String colecao, String usuario, String senha) {
        File temp = null;
        byte[] bytesArray = null;
        FileInputStream fileInputStream = null;
        try {
            temp = File.createTempFile("tempfile", ".tmp");
        } catch (IOException ex) {
            Logger.getLogger(RetrieveMongo.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {

            BasicDBObject basicDBObject = new BasicDBObject();
            basicDBObject.put("_id", new ObjectId(objectID));

            DB db = Connection.getInstance().getClient(Connection.ENDERECOLEITURA2, Connection.PORTALEITURA2, usuario, senha).getDB(banco);

            GridFS gridFS = new GridFS(db, colecao);
            GridFSDBFile gridFSDBFile = gridFS.findOne(basicDBObject);

            gridFSDBFile.writeTo(temp);

            bytesArray = new byte[(int) temp.length()];

            //read file into bytes[]
            fileInputStream = new FileInputStream(temp);
            fileInputStream.read(bytesArray);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        return bytesArray;
    }
}
