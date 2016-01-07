package com.videumcorp.desarrolladorandroid.navigationdrawerandroiddesignsupportlibrary.MyAdapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.videumcorp.desarrolladorandroid.navigationdrawerandroiddesignsupportlibrary.Company.FormContReq;
import com.videumcorp.desarrolladorandroid.navigationdrawerandroiddesignsupportlibrary.R;

import java.util.ArrayList;

public class AdapterSelEst extends ArrayAdapter<Establishment> {

    private final Context context;
    private final String name;
    private final ArrayList<Establishment> itemsArrayList;
    private String access_token;
    private String client;
    private String uid;

    public AdapterSelEst(Context context, ArrayList<Establishment> itemsArrayList, String empresa, String access_token, String client, String uid) {

        super(context, R.layout.item_list_establishment_container, itemsArrayList);

        this.context = context;
        this.itemsArrayList = itemsArrayList;
        this.name = empresa;
        this.access_token = access_token;
        this.client = client;
        this.uid = uid;
    }

    @Override
    public View getView(final int position, final View convertView, ViewGroup parent) {

        // 1. Create inflater
        final LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // 2. Get rowView from inflater
        View rowView = inflater.inflate(R.layout.item_list_establishment_container, parent, false);

        // 3. Get the two text view from the rowView
        TextView EstablishmentName = (TextView) rowView.findViewById(R.id.ContainerName);
        final Button btCambiar = (Button) rowView.findViewById(R.id.btCambiar);
        Button mostrar = (Button) rowView.findViewById(R.id.ContainerStatus);
        ImageView imContenedor = (ImageView) rowView.findViewById(R.id.move_poster);

        // 4. Set the text for textView
        EstablishmentName.setText(itemsArrayList.get(position).getName());
        imContenedor.setImageResource(R.drawable.fundacion);

            mostrar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                    builder.setMessage(
                            "Nombre de la fundacion: " + "\n" + itemsArrayList.get(position).getName() + "\n" +
                                    "\n" + "Ubicacion: " + "\n" + itemsArrayList.get(position).getAddress() + "\n" +
                                    "\n" + "Correo: " + "\n" + itemsArrayList.get(position).getEmail() + "\n" +
                                    "\n" + "Tipo de desecho: " + "\n" + itemsArrayList.get(position).getWaste() + "\n"
                    );
                    builder.setTitle("Datos de la fundacion");
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            });


        btCambiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                String nombre = itemsArrayList.get(position).getName();
                String establishment_id = itemsArrayList.get(position).getId();
                String waste = itemsArrayList.get(position).getWaste();
                String address = itemsArrayList.get(position).getAddress();
                String empresa = name;
                String desecho;

                switch (waste) {
                    case "Papel":
                        desecho = "1";
                        break;

                    case "Plastico":
                        desecho = "2";
                        break;

                    case "Vidrio":
                        desecho = "3";
                        break;

                    case "Lata":
                        desecho = "4";
                        break;

                    default:
                        desecho = "Otro desecho";
                        break;
                }
                //    String fundacion = listView.getItemAtPosition(position).toString();
                //   String fundacion = Integer.toString(position);

                Intent intent = new Intent(v.getContext(), FormContReq.class);
                intent.putExtra(FormContReq.WASTE, desecho);
                intent.putExtra(FormContReq.ESTABLISHMENT_ID, establishment_id);
                intent.putExtra(FormContReq.FUNDACION, nombre);
                intent.putExtra(FormContReq.EMPRESA, empresa);
                intent.putExtra(FormContReq.ADDRESS, address);
                intent.putExtra("access-token", access_token);
                intent.putExtra("client", client);
                intent.putExtra("uid", uid);

                v.getContext().startActivity(intent);
            }
        });


        // 5. retrn rowView
        return rowView;
    }

}

