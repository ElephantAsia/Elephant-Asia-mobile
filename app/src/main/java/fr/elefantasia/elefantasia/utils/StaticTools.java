package fr.elefantasia.elefantasia.utils;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;


/**
 * Created by Stephane on 31/10/2016.
 */

public class StaticTools {

    /**
     * Copie un flux de données entrant vers un flux de données sortant.
     *
     * @param input  Flux entrant
     * @param output Flux sortant
     * @return TRUE si la copie a réussi
     */
    public static boolean copyStreamToStream(
            @NonNull InputStream input,
            @NonNull OutputStream output
    ) {
        boolean result = false;

        try {
            byte[] buffer = new byte[1024];
            int bytesRead;

            while ((bytesRead = input.read(buffer)) > 0) {
                output.write(buffer, 0, bytesRead);
            }
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            input.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (result);
    }

    /**
     * Convertit un flux de données entrant en un tableau d'octet.
     *
     * @param input Flux entrant
     * @return Un tableau de byte
     */
    @NonNull
    public static byte[] getByteArrayFromStream(
            @NonNull InputStream input
    ) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();

        copyStreamToStream(input, output);
        return (output.toByteArray());
    }

    /**
     * Converti un flux de données entrant en un texte encodé avec charset.
     *
     * @param input   Flux entrant
     * @param charset Le charset du texte
     * @return La chaîne de caractères ou NULL si échec
     */
    @Nullable
    public static String getStringFromStream(
            @NonNull InputStream input,
            @NonNull String charset
    ) {
        try {
            return (new String(getByteArrayFromStream(input), charset));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return (null);
        }
    }
}