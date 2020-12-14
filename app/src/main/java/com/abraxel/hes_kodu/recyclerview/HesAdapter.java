package com.abraxel.hes_kodu.recyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.abraxel.hes_kodu.R;
import com.abraxel.hes_kodu.entity.HesModel;
import java.util.List;

public class HesAdapter extends RecyclerView.Adapter<HesAdapter.ViewHolder> {
    private OnItemClickListener mListener;

    public interface OnItemClickListener{
        void onItemClick(int position);
        void onShareItemClick(int position);
        void onCopyClick(int position);
        void onGoQRClick(int position);
        void onDeleteClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    private Context context;
    private List<HesModel> hesModel;

    public HesAdapter(Context context, List<HesModel> hesModel) {
        this.context = context;
        this.hesModel = hesModel;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_hescodes_list, parent, false);
        return new ViewHolder(view, mListener   );
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        HesModel model = hesModel.get(position);

        holder.textName.setText(model.getName());
        holder.textHesCode.setText(model.getHesCode());
        holder.textQRCode.setText(model.getQrCode());

    }

    @Override
    public int getItemCount() {
        return hesModel.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView textName;
        private TextView textHesCode;
        private TextView textQRCode;
        private ImageView shareIcon, copyIcon, deleteIcon, goToQRLink;

        public ViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            textName = itemView.findViewById(R.id.txtNameCard);
            textHesCode = itemView.findViewById(R.id.txtHesCard);
            textQRCode = itemView.findViewById(R.id.txtQrCard);
            shareIcon = itemView.findViewById(R.id.shareCardIcon);
            copyIcon = itemView.findViewById(R.id.copyCardIcon);
            deleteIcon = itemView.findViewById(R.id.deleteCardIcon);
            goToQRLink = itemView.findViewById(R.id.goQRLinkCardIcon);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listener.onItemClick(position);
                        }
                    }

                }
            });

            copyIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listener.onCopyClick(position);
                        }
                    }
                }
            });

            goToQRLink.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listener.onGoQRClick(position);
                        }
                    }
                }
            });

            shareIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listener.onShareItemClick(position);
                        }
                    }
                }
            });

            deleteIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listener.onDeleteClick(position);
                        }
                    }
                }
            });


        }

    }


}
