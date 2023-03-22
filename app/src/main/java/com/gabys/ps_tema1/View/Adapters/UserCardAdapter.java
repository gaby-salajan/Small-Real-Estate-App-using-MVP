package com.gabys.ps_tema1.View.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.gabys.ps_tema1.Model.Property;
import com.gabys.ps_tema1.Model.User;
import com.gabys.ps_tema1.Presenter.PresenterAdmin;
import com.gabys.ps_tema1.Presenter.PresenterEmployee;
import com.gabys.ps_tema1.R;
import com.gabys.ps_tema1.View.EditPropertyActivity;
import com.gabys.ps_tema1.View.EditUserActivity;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class UserCardAdapter extends RecyclerView.Adapter<UserCardAdapter.ViewHolder> {
    private final Context context;
    private ArrayList<User> userList;

    private PresenterAdmin presenter;


    public UserCardAdapter(Context context) {
        this.context = context;
        this.userList = new ArrayList<>();
    }

    public UserCardAdapter(Context context, ArrayList<User> users) {
        this.context = context;
        this.userList = new ArrayList<>(users);
    }


    public void setItems(ArrayList<User> users) {
        this.userList.clear();
        this.userList.addAll(users);
        notifyDataSetChanged();
    }

    public void addPresenter(PresenterAdmin presenter){
        this.presenter = presenter;
    }


    @NonNull
    @Override
    public UserCardAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_user, parent, false);
        return new UserCardAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserCardAdapter.ViewHolder holder, int position) {
        User model = userList.get(position);

        holder.userId.setText("ID: " + String.valueOf(model.getId()));
        holder.userUsername.setText("Nume utilizator: " + model.getUsername());
        holder.userPassword.setText("Parola:" + model.getPassword());

        ArrayList<String> types = Arrays.stream(context.getResources().getStringArray(R.array.user_roles)).collect(Collectors.toCollection(ArrayList::new));
        holder.userRole.setText(types.get(model.getRole()));

        holder.modifyButton.setOnClickListener(v -> {
            Intent intent = new Intent(context, EditUserActivity.class);

            Gson gson = new Gson();
            String myJson = gson.toJson(model);
            intent.putExtra("user", myJson);

            context.startActivity(intent);
        });

        holder.deleteButton.setOnClickListener(v -> {
            presenter.deleteUser(model.getId());
        });
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    // View holder class for initializing of your views such as TextView and Imageview
    public static class ViewHolder extends RecyclerView.ViewHolder {
        //complete with card details

        private final TextView userId;
        private final TextView userUsername;
        private final TextView userPassword;
        private final TextView userRole;
        private final Button modifyButton;
        private final Button deleteButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            userId = itemView.findViewById(R.id.card_id);
            userUsername = itemView.findViewById(R.id.card_username);
            userPassword = itemView.findViewById(R.id.card_password);
            userRole = itemView.findViewById(R.id.card_role);

            modifyButton = itemView.findViewById(R.id.modify_button);
            deleteButton = itemView.findViewById(R.id.delete_button);
        }
    }
}
