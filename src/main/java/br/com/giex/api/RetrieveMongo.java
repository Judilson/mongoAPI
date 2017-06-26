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
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bson.types.ObjectId;

/**
 *
 * @author jjunior
 */
public class RetrieveMongo {

    public File retrieve(String objectID, String banco, String colecao, String usuario, String senha) {
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
//        finally {
//            temp.delete();
//        }

        return temp;
    }

    public File retrieve(String key, Object value, String banco, String colecao, String usuario, String senha) {
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
//        finally {
//            temp.delete();
//        }

        return temp;
    }

}
