package com.practica02.mbussiness.fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.firestore.Query;
import com.practica02.mbussiness.R;
import com.practica02.mbussiness.adapters.ArticleAdapter;
import com.practica02.mbussiness.dialogs.article.ArticleAddDialog;
import com.practica02.mbussiness.dialogs.article.ArticleDeleteDialog;
import com.practica02.mbussiness.dialogs.article.ArticleModifyDialog;
import com.practica02.mbussiness.dialogs.article.ArticleViewDialog;
import com.practica02.mbussiness.model.dto.ArticleDTO;
import com.practica02.mbussiness.model.entity.Article;
import com.practica02.mbussiness.model.mapper.ArticleMapper;
import com.practica02.mbussiness.repository.ArticleRepository;
import com.practica02.mbussiness.repository.FirestoreRepository;
import com.practica02.mbussiness.repository.RequirementsRepository;
import com.practica02.mbussiness.repository.liveData.MultipleDocumentReferenceLiveData;
import com.practica02.mbussiness.viewmodel.ArticleViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ArticleFragment extends Fragment {

    private final String TAG = ArticleFragment.class.getSimpleName();

    private RecyclerView recyclerViewArticle;
    private Button btnAddArticle;
    private EditText editSearch;
    private ImageButton btnSearch;
    private ArticleViewModel articleViewModel;
    private ArticleAdapter articleAdapter;

    private Spinner fieldArticle, orderArticle;
    private CheckBox active, inactive, eliminated;
    private Button btnFilter;

    private Query collectionQuery;
    private Query filterResultQuery;
    private MediatorLiveData<List<? extends Article>> resultLiveData;
    private MultipleDocumentReferenceLiveData<Article, ?> allLiveData;
    private MultipleDocumentReferenceLiveData<Article, ?> activeLiveData;
    private MultipleDocumentReferenceLiveData<Article, ?> liveDataByRegistry;
    private MultipleDocumentReferenceLiveData<Article, ?> liveDataByName;
    private MultipleDocumentReferenceLiveData<Article, ?> filterLiveData;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_article, container, false);

        this.fieldArticle = view.findViewById(R.id.field_article);
        this.orderArticle = view.findViewById(R.id.order_article);
        this.active = view.findViewById(R.id.check_active_article);
        this.inactive = view.findViewById(R.id.check_inactive_article);
        this.eliminated = view.findViewById(R.id.check_eliminated_article);
        this.btnFilter = view.findViewById(R.id.btn_filter_article);

        this.recyclerViewArticle = view.findViewById(R.id.rvArticle);
        this.btnAddArticle = view.findViewById(R.id.addArticle);
        this.editSearch = view.findViewById(R.id.editSearchArticle);
        this.btnSearch = view.findViewById(R.id.searchArticle);

        ArrayAdapter<CharSequence> fieldAdapter = ArrayAdapter.createFromResource(getContext(), R.array.filtro_article, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> orderAdapter = ArrayAdapter.createFromResource(getContext(), R.array.orden, android.R.layout.simple_spinner_item);
        fieldArticle.setAdapter(fieldAdapter);
        orderArticle.setAdapter(orderAdapter);

        this.fieldArticle.setAdapter(fieldAdapter);
        this.orderArticle.setAdapter(orderAdapter);
        this.articleAdapter = new ArticleAdapter(this.getContext());
        this.articleViewModel = new ViewModelProvider(this).get(ArticleViewModel.class);
        this.collectionQuery = this.articleViewModel.getBuilderQuery();
        this.filterResultQuery = this.articleViewModel.getBuilderQuery();
        this.resultLiveData = new MediatorLiveData<>();

        this.articleAdapter.setViewListener(data -> {
            createViewDialog(ArticleMapper.getMapper().entityToDto(data));
            this.articleAdapter.notifyDataSetChanged();
        });
        this.articleAdapter.setModifyListener(data -> {
            createModifyDialog(ArticleMapper.getMapper().entityToDto(data));
            this.articleAdapter.notifyDataSetChanged();
        });
        this.articleAdapter.setDeleteListener(data -> {
            createDeleteDialog(ArticleMapper.getMapper().entityToDto(data));
            this.articleAdapter.notifyDataSetChanged();
        });
        this.btnAddArticle.setOnClickListener(v -> {
            createAddDialog();
            this.articleAdapter.notifyDataSetChanged();
        });
        this.recyclerViewArticle.setLayoutManager(new LinearLayoutManager(this.getContext()));
        this.recyclerViewArticle.setAdapter(this.articleAdapter);
//=====================================
        this.activeLiveData = this.articleViewModel.getActiveBrandLiveData();
        this.resultLiveData.addSource(this.activeLiveData, articles -> resultLiveData.setValue(articles));
        this.active.setChecked(true);
//=====================================
//        this.allLiveData = this.articleViewModel.getAllArticleLiveData();
//        this.resultLiveData.addSource(this.allLiveData, articles -> resultLiveData.setValue(articles));

        this.editSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().isEmpty()) {
                    cleanResults();
                    resultLiveData.addSource(allLiveData, articles -> resultLiveData.setValue(articles));
                } else {
                    Log.e(TAG, "No change data result");
                }
            }
        });

        this.btnSearch.setOnClickListener(v -> {
            String searchString = this.editSearch.getText().toString();
            Query queryByName = ArticleRepository.Companion.filterByName(this.collectionQuery, searchString);
            Query queryByRegistryState = FirestoreRepository.Companion.filterByRegistryState(this.collectionQuery, searchString);
            this.liveDataByName = this.articleViewModel.queryLiveData(queryByName);
            this.liveDataByRegistry = this.articleViewModel.queryLiveData(queryByRegistryState);
            this.cleanResults();
            Log.e(TAG, "Add New Resource");
            this.resultLiveData.addSource(this.liveDataByName, articles -> {
                List<Article> articleList = new ArrayList<>(articles);
                articleList.addAll(Objects.requireNonNull(this.resultLiveData.getValue()));
                this.resultLiveData.setValue(articleList);
            });
            this.resultLiveData.addSource(this.liveDataByRegistry, articles -> {
                List<Article> articleList = new ArrayList<>(articles);
                articleList.addAll(Objects.requireNonNull(this.resultLiveData.getValue()));
                this.resultLiveData.setValue(articleList);
            });
        });

        this.btnFilter.setOnClickListener(v -> {
            this.filterResultQuery = this.articleViewModel.getBuilderQuery();
            List<String> registryStateFilter = new ArrayList<>();
            if (this.active.isChecked())
                registryStateFilter.add(RequirementsRepository.ACTIVE);
            if (this.inactive.isChecked())
                registryStateFilter.add(RequirementsRepository.INACTIVE);
            if (this.eliminated.isChecked())
                registryStateFilter.add(RequirementsRepository.ELIMINATED);
            if (registryStateFilter.size() == 1) {
                this.filterResultQuery = FirestoreRepository.Companion
                        .filterByRegistryState(this.filterResultQuery, registryStateFilter.get(0));
            } else if (registryStateFilter.size() > 1) {
                this.filterResultQuery = FirestoreRepository.Companion
                        .filterByRegistryStates(this.filterResultQuery, registryStateFilter);
            }
            if (fieldArticle.getSelectedItem().toString().equalsIgnoreCase("Nombre")) {
                Log.e(TAG, "Filter Nombre");
                if (orderArticle.getSelectedItem().toString().equalsIgnoreCase("Ascendente")) {
                    Log.e(TAG, "Filter ASC");
                    this.filterResultQuery = ArticleRepository.Companion.orderAscendingByName(this.filterResultQuery);
                } else if (orderArticle.getSelectedItem().toString().equalsIgnoreCase("Descendente")) {
                    Log.e(TAG, "Filter DESC");
                    this.filterResultQuery = ArticleRepository.Companion.orderDescendingByName(this.filterResultQuery);
                }
            } else if (fieldArticle.getSelectedItem().toString().equalsIgnoreCase("Estado de Registro")) {
                Log.e(TAG, "Filter Estado de registro");
                if (orderArticle.getSelectedItem().toString().equalsIgnoreCase("Ascendente")) {
                    Log.e(TAG, "Filter ASC");
                    this.filterResultQuery = FirestoreRepository.Companion.orderAscendingByRegistryState(this.filterResultQuery);
                } else if (orderArticle.getSelectedItem().toString().equalsIgnoreCase("Descendente")) {
                    Log.e(TAG, "Filter DESC");
                    this.filterResultQuery = FirestoreRepository.Companion.orderDescendingByRegistryState(this.filterResultQuery);
                }
            } else if (fieldArticle.getSelectedItem().toString().equalsIgnoreCase("Precio Unitario")) {
                Log.e(TAG, "Filter PrecioUnitario");
                if (orderArticle.getSelectedItem().toString().equalsIgnoreCase("Ascendente")) {
                    Log.e(TAG, "Filter ASC");
                    this.filterResultQuery = ArticleRepository.Companion.orderAscendingByUnitaryPrice(this.filterResultQuery);
                } else if (orderArticle.getSelectedItem().toString().equalsIgnoreCase("Descendente")) {
                    Log.e(TAG, "Filter DESC");
                    this.filterResultQuery = ArticleRepository.Companion.orderDescendingByUnitaryPrice(this.filterResultQuery);
                }
            }
            this.cleanResults();
            Log.e(TAG, "Add New Resource");
            this.filterLiveData = this.articleViewModel.queryLiveData(this.filterResultQuery);
            this.resultLiveData.addSource(this.filterLiveData, articles -> this.resultLiveData.setValue(articles));
        });

        this.resultLiveData.observe(this, articles -> {
            Log.e(TAG, "Updating UI");
            for (Article article : articles) {
                Log.e(TAG, article.toString());
            }
            articleAdapter.setArticle(articles);
            articleAdapter.notifyDataSetChanged();
        });


        return view;
    }

    private void cleanResults() {
        Log.e(TAG, "Remove all resources");
        if (this.allLiveData != null) {
            this.resultLiveData.removeSource(this.allLiveData);
        }
        if (this.liveDataByName != null && this.liveDataByRegistry != null) {
            this.resultLiveData.removeSource(this.liveDataByName);
            this.resultLiveData.removeSource(this.liveDataByRegistry);
        }
        if (this.filterLiveData != null) {
            this.resultLiveData.removeSource(this.filterLiveData);
        }
        if (this.activeLiveData != null) {
            this.resultLiveData.removeSource(this.activeLiveData);
        }
        this.resultLiveData.setValue(new ArrayList<>());
    }

    private void createDeleteDialog(ArticleDTO dto) {
        ArticleDeleteDialog deleteDialog = new ArticleDeleteDialog(dto);
//        deleteDialog.setOnPositiveEvent(v -> {
//            this.cleanResults();
//            this.resultLiveData.addSource(allLiveData, articles -> this.resultLiveData.setValue(articles));
//        });
        deleteDialog.show(Objects.requireNonNull(this.getFragmentManager()), TAG);
    }

    private void createModifyDialog(ArticleDTO dto) {
        ArticleModifyDialog modifyDialog = new ArticleModifyDialog(dto);
//        modifyDialog.setOnPositiveEvent(v -> {
//            this.cleanResults();
//            this.resultLiveData.addSource(allLiveData, articles -> this.resultLiveData.setValue(articles));
//        });
        modifyDialog.show(Objects.requireNonNull(this.getFragmentManager()), TAG);
    }

    private void createAddDialog() {
        ArticleAddDialog addDialog = new ArticleAddDialog();
//        addDialog.setOnPositiveEvent(v -> {
//            this.cleanResults();
//            this.resultLiveData.addSource(allLiveData, articles -> this.resultLiveData.setValue(articles));
//        });
        addDialog.show(Objects.requireNonNull(this.getFragmentManager()), TAG);
    }

    private void createViewDialog(ArticleDTO dto) {
        ArticleViewDialog viewDialog = new ArticleViewDialog(dto);
        viewDialog.show(Objects.requireNonNull(this.getFragmentManager()), TAG);
    }
}
