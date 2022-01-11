package com.example.woofinder;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.woofinder.clases.Organizacion;
import com.example.woofinder.clases.SingletonOrganizacion;
import com.example.woofinder.clases.Solicitud;
import com.example.woofinder.clases.Usuario;

import java.util.List;
import java.util.Properties;
import java.util.Random;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SolicitudAdapter extends ArrayAdapter<Solicitud> {

    private List<Solicitud> listaSolicitudes;
    public SolicitudAdapter(Context context, List<Solicitud> lista){
        super(context, 0, lista);
        listaSolicitudes = lista;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View listitemView = convertView;
        if (listitemView == null) {
            listitemView = LayoutInflater.from(getContext()).inflate(R.layout.item_solicitud, parent, false);
        }

        ListView listview = (ListView) parent;
        Organizacion org = SingletonOrganizacion.getInstance().get("ORGANIZACION");
        SolicitudAdapter solicitudAdapter = this;

        TextView nombreTextView = (TextView) listitemView.findViewById(R.id.solicitud_correo);
        Button btnAceptar = (Button) listitemView.findViewById(R.id.button_aceptar);
        Button btnEliminar = (Button) listitemView.findViewById(R.id.button_eliminar);

        Solicitud solicitud = getItem(position);

        nombreTextView.setText(solicitud.getCorreoUser());

        btnAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Random r = new Random();
                Integer randomPassword = r.nextInt(999999);
                Usuario user = new Usuario(solicitud.getCorreoUser(), "", org, randomPassword.toString());

                listaSolicitudes.remove(position);
                solicitudAdapter.notifyDataSetChanged();

                String mensaje = "Se ha aceptado su solicitud de registro en la organización "
                        +org.getNombre()+ ". Por favor, inicie sesión con las siguientes credenciales y complete sus datos:" +
                        "\nEmail: "+user.getCorreo()+
                        "\nContraseña: "+user.getPassword();

                String username = "woofinder.oficial@gmail.com";
                String password = "Buscachuchos";

                Properties properties = new Properties();
                properties.put("mail.smtp.auth", "true");
                properties.put("mail.smtp.starttls.enable", "true");
                properties.put("mail.smtp.auth", "true");
                properties.put("mail.smtp.host", "smtp.gmail.com");
                properties.put("mail.smtp.port", "587");

                Session session = Session.getInstance(properties, new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

                try {
                    Message message = new MimeMessage(session);
                    message.setFrom(new InternetAddress(username));
                    message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(user.getCorreo().trim()));
                    message.setSubject("Solicitud aceptada en Woofinder".trim());
                    message.setText(mensaje.trim());

                    Thread thread = new Thread(new Runnable() {

                        @Override
                        public void run() {
                            try  {
                                Transport.send(message);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });

                    thread.start();
                } catch (MessagingException e) {
                    e.printStackTrace();
                }

                solicitud.deleteSolicitud();

                Toast.makeText(getContext(), getContext().getString(R.string.solicitud_toast_aceptar_1)+
                        user.getCorreo() + getContext().getString(R.string.solicitud_toast_aceptar_2), Toast.LENGTH_LONG).show();
            }
        });

        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listaSolicitudes.remove(position);
                solicitudAdapter.notifyDataSetChanged();

                solicitud.deleteSolicitud();

                Toast.makeText(getContext(), getContext().getString(R.string.solicitud_toast_eliminar), Toast.LENGTH_LONG).show();
            }
        });

        return listitemView;
    }
}
