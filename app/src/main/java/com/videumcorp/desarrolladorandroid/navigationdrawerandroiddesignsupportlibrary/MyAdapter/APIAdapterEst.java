package com.videumcorp.desarrolladorandroid.navigationdrawerandroiddesignsupportlibrary.MyAdapter;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.videumcorp.desarrolladorandroid.navigationdrawerandroiddesignsupportlibrary.Establishment.APIMain;
import com.videumcorp.desarrolladorandroid.navigationdrawerandroiddesignsupportlibrary.R;
import com.videumcorp.desarrolladorandroid.navigationdrawerandroiddesignsupportlibrary.DataBase.RcycloDatabaseHelper;

import java.util.ArrayList;

public class APIAdapterEst extends ArrayAdapter<Container> {

    private final Context context;
    private final ArrayList<Container> itemsArrayList;
    private SQLiteDatabase db;

    public APIAdapterEst(Context context, ArrayList<Container> itemsArrayList) {

        super(context, R.layout.item_list_establishment, itemsArrayList);

        this.context = context;
        this.itemsArrayList = itemsArrayList;
    }

    @Override
    public View getView(final int position, final View convertView, ViewGroup parent) {

        // 1. Create inflater
        final LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // 2. Get rowView from inflater
        View rowView = inflater.inflate(R.layout.item_list_establishment, parent, false);

        // 3. Get the two text view from the rowView
        TextView ContainerName = (TextView) rowView.findViewById(R.id.ContainerName);
        TextView ContainerStatus = (TextView) rowView.findViewById(R.id.ContainerStatus);
        final Button btCambiar = (Button) rowView.findViewById(R.id.btCambiar);
        ImageView imContenedor = (ImageView) rowView.findViewById(R.id.move_poster);


        // 4. Set the text for textView
        ContainerStatus.setText("Ver Solicitud");
        imContenedor.setImageResource(R.drawable.solicitud);

            imContenedor.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                    builder.setMessage(
                            "Nombre del contenedor: " + "\n" + itemsArrayList.get(position).getNameContainer() + "\n" +
                                    "\n" + "Ubicacion: " + "\n" + itemsArrayList.get(position).getLatlong() + "\n" +
                                    "\n" + "Empresa Asociada: " + "\n" + itemsArrayList.get(position).getCompany() + "\n" +
                                    "\n" + "Estado del contenedor: " + "\n" + itemsArrayList.get(position).getStatus() + "\n" +
                                    "\n" + "Tipo de desecho: " + "\n" + itemsArrayList.get(position).getDesecho() + "\n");
                    builder.setTitle("Datos del contenedor");
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
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setMessage(
                        "¿Desea aceptar la solicitud y activar este contenedor?");
                        builder.setTitle("Aceptar solicitud");
                        builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                SQLiteOpenHelper rcycloDatabaseHelper = new RcycloDatabaseHelper(v.getContext());
                                db = rcycloDatabaseHelper.getWritableDatabase();
                                ContentValues containerValues = new ContentValues();
                                containerValues.put("ACTIVO", "ACTIVO");
                                //Implementar API Aqui!!
                                db.update("CONTAINER", containerValues, "NAME_CONTAINER = ? AND COMPANY = ?", new String[]{itemsArrayList.get(position).getNameContainer(), itemsArrayList.get(position).getCompany()});
                                db.close();
                                itemsArrayList.remove(itemsArrayList.get(position));
                                APIMain activity = (APIMain)context;
                                activity.finish();
                                activity.startActivity(activity.getIntent());

                            }

                        });
                        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
        // 5. retrn rowView
        return rowView;
    }

}

