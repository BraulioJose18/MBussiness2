package com.practica02.mbussiness.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.practica02.mbussiness.R;
import com.practica02.mbussiness.dialogs.article.ArticleViewDialog;
import com.practica02.mbussiness.model.dto.ArticleDTO;
import com.practica02.mbussiness.model.entity.Article;
import com.practica02.mbussiness.model.mapper.ArticleMapper;
import com.practica02.mbussiness.utils.OnClickDataListener;

import java.util.ArrayList;
import java.util.List;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ViewHolder> {

    private final LayoutInflater inflater;
    private final List<Article> articleList;

    private OnClickDataListener<Article> viewListener;
    private OnClickDataListener<Article> modifyListener;
    private OnClickDataListener<Article> deleteListener;

    public ArticleAdapter(Context context) {
        this.inflater = LayoutInflater.from(context);
        this.articleList = new ArrayList<>();
    }

    @NonNull
    @Override
    public ArticleAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_article, parent, false);
        return new ArticleAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ArticleAdapter.ViewHolder holder, int position) {
        ArticleDTO dto = ArticleMapper.getMapper().entityToDto(this.articleList.get(position));
        holder.id.setText(dto.getIdentifier());
        holder.name.setText(dto.getName());
        holder.unitaryPrice.setText(dto.getUnitaryPrice());
        holder.registryState.setText(ArticleViewDialog.getSpinnerFromRegistryState(dto.getRegistryState()));
        holder.view.setOnClickListener(v -> this.viewListener.onClick(this.articleList.get(position)));
        holder.modify.setOnClickListener(v -> this.modifyListener.onClick(this.articleList.get(position)));
        holder.delete.setOnClickListener(v -> this.deleteListener.onClick(this.articleList.get(position)));
    }

    @Override
    public int getItemCount() {
        return this.articleList.size();
    }

    public void setArticle(List<? extends Article> articleList) {
        this.articleList.clear();
        this.articleList.addAll(articleList);
    }

    public void setViewListener(OnClickDataListener<Article> viewListener) {
        this.viewListener = viewListener;
    }

    public void setModifyListener(OnClickDataListener<Article> modifyListener) {
        this.modifyListener = modifyListener;
    }

    public void setDeleteListener(OnClickDataListener<Article> deleteListener) {
        this.deleteListener = deleteListener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        protected final TextView id, name, unitaryPrice, registryState;
        protected final Button view, modify, delete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.id = itemView.findViewById(R.id.idArticle);
            this.name = itemView.findViewById(R.id.nameArticle);
            this.unitaryPrice = itemView.findViewById(R.id.unitaryPriceArticle);
            this.registryState = itemView.findViewById(R.id.registryStateArticle);
            this.view = itemView.findViewById(R.id.bViewArticle);
            this.modify = itemView.findViewById(R.id.bModifyArticle);
            this.delete = itemView.findViewById(R.id.bDeleteArticle);

        }
    }
}
