package edu.ucsb.cs.cs48.schedoptim.onboarding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.ucsb.cs.cs48.schedoptim.R;

public class OnboardingAdapter extends RecyclerView.Adapter<OnboardingAdapter.OnboardingViewHolder> {

    private List<OnboardingItem> onboardingItemList;

    public OnboardingAdapter(List<OnboardingItem> onboardingItemList) {
        this.onboardingItemList = onboardingItemList;
    }

    @NonNull
    @Override
    public OnboardingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new OnboardingViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.item_container_onboarding, parent, false
                )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull OnboardingViewHolder holder, int position) {
        holder.setOnboardingData(onboardingItemList.get(position));
    }

    @Override
    public int getItemCount() {
        return onboardingItemList.size();
    }

    class OnboardingViewHolder extends RecyclerView.ViewHolder {
        private ImageView image;

        OnboardingViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.imageOnboarding);
        }

        void setOnboardingData(OnboardingItem onboardingItem) {
            image.setImageResource(onboardingItem.getImage());
        }
    }
}
