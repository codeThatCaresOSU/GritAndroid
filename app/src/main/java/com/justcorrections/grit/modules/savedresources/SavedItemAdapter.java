package com.justcorrections.grit.modules.savedresources;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.justcorrections.grit.R;
import com.justcorrections.grit.data.model.Resource;

import java.util.List;

public class SavedItemAdapter extends RecyclerView.Adapter<SavedItemAdapter.ViewHolder> {

    private List<Resource> resourceList;
    private SavedResourcesFragment savedResourcesFragment;

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView resourceName;

        public ViewHolder(View itemView) {
            super(itemView);
            resourceName = itemView.findViewById(R.id.tv_saved_item_name);
        }
    }

    public SavedItemAdapter(List<Resource> resourceList, SavedResourcesFragment savedResourcesFragment) {
        this.resourceList = resourceList;
        this.savedResourcesFragment = savedResourcesFragment;
    }

    @NonNull
    @Override
    public SavedItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.saved_resource_item, parent, false);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                savedResourcesFragment.itemOnClick(view);
            }
        });
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SavedItemAdapter.ViewHolder holder, int position) {
        Resource resource = resourceList.get(position);
        holder.resourceName.setText(resource.getName());
    }

    @Override
    public int getItemCount() {
        return resourceList.size();
    }

}
