package fr.bts.sio.Utils;

public class ConvertirDate {
    public static java.sql.Date convertirVersSQL(java.util.Date dateJava) {
        if (dateJava == null) {
            return null;
        }
        return new java.sql.Date(dateJava.getTime());
    }

    public static java.util.Date convertirVersJava(java.sql.Date dateSql) {
        if (dateSql == null) {
            return null;
        }
        return new java.util.Date(dateSql.getTime());

    }
}
