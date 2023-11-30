package mx.GPS.healthec.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import mx.GPS.healthec.R;
import mx.GPS.healthec.modelos.Recetas;

public class RecetasAdapter extends RecyclerView.Adapter<RecetasAdapter.ViewHolder> {

    private OnItemClickListener mListener;
    private ArrayList<Recetas> recetasList;
    private ArrayList<Recetas> ListOriginal;

    public RecetasAdapter(ArrayList<Recetas> recetas){
        this.recetasList = recetas;
        ListOriginal = new ArrayList<>();
        ListOriginal.addAll(recetasList);
    }

    public void filtrado(String txtBuscar){
        if(txtBuscar.length() == 0){
            recetasList.clear();
            recetasList.addAll(ListOriginal);
        }else{
            List<Recetas> collection = recetasList.stream().filter(i -> i.getTitulo().toLowerCase().
                    contains(txtBuscar.toLowerCase())).collect(Collectors.toList());
            recetasList.clear();
            recetasList.addAll(collection);
        }
        notifyDataSetChanged();
    }

    @Override
    public RecetasAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_recetas, parent, false);
        return new RecetasAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Recetas recetas = recetasList.get(position);
        holder.Titulo.setText(recetas.getTitulo());
        holder.Descripcion.setText(recetas.getDescripcion());
        holder.Imagen.setImageResource(recetas.getImagen());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mListener != null){
                    mListener.onItemClick(recetas);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return recetasList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView Titulo,Descripcion;
        ImageView Imagen;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Titulo = itemView.findViewById(R.id.txtvTituloRecetas);
            Descripcion = itemView.findViewById(R.id.txtvDescripcionRecetas);
            Imagen = itemView.findViewById(R.id.imgvRecetas);
        }
    }

    public interface OnItemClickListener{
        void onItemClick(Recetas recetas);
    }
    public void setOnItemClickListener(OnItemClickListener Listener){
        mListener = Listener;
    }
}
