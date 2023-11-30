package mx.GPS.healthec;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class ListAdapter extends ArrayAdapter <Modelo> {

    private List<Modelo> milista;
    private Context mContext;
    private int resourceLayout;

    public ListAdapter(@NonNull Context context, int resource, List<Modelo> objects) {
        super(context, resource, objects);
        this.milista=objects;
        this.mContext=context;
        this.resourceLayout=resource;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view=convertView;
        if(view==null)
            view= LayoutInflater.from(mContext).inflate(R.layout.item_row,null);

        Modelo modelo= milista.get(position);
        TextView textoact= view.findViewById(R.id.txtactividad);
        textoact.setText(modelo.getNombreactividad());
        TextView textoprioridad= view.findViewById(R.id.txtprioridad);
        textoprioridad.setText(modelo.getPrioridadactividad());
        return view;
    }


}
