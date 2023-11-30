package mx.GPS.healthec.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import mx.GPS.healthec.R;
import mx.GPS.healthec.modelos.Ingredientes;

public class IngredientesAdapter extends RecyclerView.Adapter<IngredientesAdapter.ViewHolder> {
    private OnItemClickListener mListener;
    private ArrayList<Ingredientes> ingredientesList;
    private  ArrayList<Ingredientes> ListOriginal;

    public IngredientesAdapter(ArrayList<Ingredientes> ingredientes){
        this.ingredientesList = ingredientes;
        ListOriginal = new ArrayList<>();
        ListOriginal.addAll(ingredientesList);

    }

    public void filtrado (String txtBuscar){
        if(txtBuscar.length() == 0){
            ingredientesList.clear();
            ingredientesList.addAll(ListOriginal);
        }else{
            List<Ingredientes> collection = ingredientesList.stream().filter(i -> i.getTitulo().toLowerCase().
                    contains(txtBuscar.toLowerCase())).collect(Collectors.toList());
            ingredientesList.clear();
            ingredientesList.addAll(collection);
            /*
            for(Ingredientes i: ListOriginal){
                if(i.getTitulo().toLowerCase().contains(txtBuscar.toLowerCase())){
                    ingredientesList.add(i);
                }
             */

        }
        notifyDataSetChanged();
    }

    @Override
    public IngredientesAdapter.ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_ingredientes, parent, false);
        return new IngredientesAdapter.ViewHolder(v);
    }


    @Override
    public void onBindViewHolder( ViewHolder holder, int position) {
        Ingredientes ingredientes = ingredientesList.get(position);
        holder.Titulo.setText(ingredientes.getTitulo());
        holder.Imagen.setImageResource(ingredientes.getImagen());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mListener != null){
                    mListener.onItemClick(ingredientes);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return ingredientesList.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView Titulo;
        ImageView Imagen;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Titulo = itemView.findViewById(R.id.txtvTituloIngredientes);
            Imagen = itemView.findViewById(R.id.imgvIngredientes);
        }
    }

    public interface OnItemClickListener{
        void onItemClick(Ingredientes ingredientes);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }
}
