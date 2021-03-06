package com.nlptech.function.languagesetting.langadded;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import com.android.inputmethod.latin.R;

import org.jetbrains.annotations.NotNull;

public class SelectLayoutSetDialogFragment extends DialogFragment {
    public static final String DIALOG_FRAGMENT = "select_layoutset_dialog_fragment";

    protected Activity mActivity;
    protected AlertDialog mDialog;

    private String mCharset;
    private String[] mLayoutSets;
    private String mCurrentLayoutSet;

    private Adapter mAdapter;

    public interface SelectLayoutSetListener {
        void onLayoutSetChanged(String charset, String newLayout);
    }
    private SelectLayoutSetListener mSelectLayoutSetListener;

    public static SelectLayoutSetDialogFragment newInstance() {
        return new SelectLayoutSetDialogFragment();
    }

    public void setSelectLayoutSetListener(SelectLayoutSetListener layoutSetListener) {
        mSelectLayoutSetListener = layoutSetListener;
    }

    public void setCharset(String charset) {
        mCharset = charset;
    }

    public void setLayoutSets(String[] layoutSets) {
        this.mLayoutSets = layoutSets;
    }

    public void setCurrentLayoutSet(String currentLayoutSet) {
        mCurrentLayoutSet = currentLayoutSet;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = getActivity();
    }

    @NotNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = LayoutInflater.from(mActivity).inflate(R.layout.dialogfg_select_layoutset, null, false);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        mAdapter = new Adapter(mLayoutSets, mCurrentLayoutSet, layoutSet -> {
            mCurrentLayoutSet = layoutSet;
            mAdapter.setCurrentLayoutSet(layoutSet);

            if (mSelectLayoutSetListener != null) mSelectLayoutSetListener.onLayoutSetChanged(mCharset, mCurrentLayoutSet);

        });
        LinearLayoutManager layoutManager = new LinearLayoutManager(mActivity,RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter);
        mDialog = new AlertDialog.Builder(mActivity).setView(view).create();
        return mDialog;
    }

    private class Adapter extends RecyclerView.Adapter<LayoutSetViewHolder> {

        String[] layoutSets;
        String currentLayoutSet;
        LayoutSetViewHolderListener layoutSetViewHolderListener;

        private Adapter(String[] layoutSets, String currentLayoutSet, LayoutSetViewHolderListener listener) {
            this.layoutSets = layoutSets;
            this.currentLayoutSet = currentLayoutSet;
            this.layoutSetViewHolderListener = listener;
        }

        public void setCurrentLayoutSet(String currentLayoutSet) {
            this.currentLayoutSet = currentLayoutSet;
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public LayoutSetViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View itemView = LayoutInflater.from(mActivity).inflate(R.layout.viewholder_select_layoutset_dialogfg_item, viewGroup, false);
            return new LayoutSetViewHolder(itemView, layoutSetViewHolderListener);
        }

        @Override
        public void onBindViewHolder(@NonNull LayoutSetViewHolder viewHolder, int i) {
            viewHolder.bind(layoutSets[i]);
        }

        @Override
        public int getItemCount() {
            return layoutSets == null ? 0 : layoutSets.length;
        }
    }

    private interface LayoutSetViewHolderListener {
        void onClick(String layoutSet);
    }

    private class LayoutSetViewHolder extends RecyclerView.ViewHolder {

        LayoutSetViewHolderListener layoutSetViewHolderListener;
        RadioButton radioButton;

        LayoutSetViewHolder(@NonNull View itemView, LayoutSetViewHolderListener listener) {
            super(itemView);
            radioButton = itemView.findViewById(R.id.radio_button);
            layoutSetViewHolderListener = listener;
        }

        void bind(final String layoutSet) {
            radioButton.setChecked(mCurrentLayoutSet.equals(layoutSet));
            radioButton.setText(layoutSet);
            radioButton.setOnClickListener(v -> layoutSetViewHolderListener.onClick(layoutSet));
        }
    }
}
