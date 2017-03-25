package com.githubusers.presentation.features.users.list;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.githubusers.presentation.R;
import com.githubusers.presentation.features.users.UserModel;
import com.githubusers.presentation.utils.glide.GlideLoader;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

/**
 * Adapter to handle user's data on recycler view
 */
public class UserDataAdapter extends RecyclerView.Adapter<UserDataAdapter.ViewHolder> {
  private List<UserModel> users;
  private GlideLoader glideLoader;

  @Inject
  public UserDataAdapter(GlideLoader glideLoader) {
    this.glideLoader = glideLoader;
    this.users = Collections.emptyList();
  }

  @Override
  public UserDataAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
    View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_user_row_layout, viewGroup, false);
    return new ViewHolder(view);
  }

  @Override
  public void onBindViewHolder(UserDataAdapter.ViewHolder viewHolder, int i) {
    viewHolder.userNameTextView.setText(users.get(i).getLogin());
    glideLoader.load(users.get(i).getAvatarUrl(),240,120,viewHolder.avatarImageView);
  }

  @Override public int getItemCount() {
    return (this.users != null) ? this.users.size() : 0;
  }

  @Override public long getItemId(int position) {
    return position;
  }

  public void setUsers(Collection<UserModel> usersCollection) {
    if(usersCollection != null) {
      this.users = (List<UserModel>) usersCollection;
      this.notifyDataSetChanged();
    } else
        throw new IllegalArgumentException("List of users cannot not be null");
  }

  public class ViewHolder extends RecyclerView.ViewHolder{
    private TextView userNameTextView;
    private ImageView avatarImageView;
    public ViewHolder(View view) {
      super(view);

      userNameTextView = (TextView)view.findViewById(R.id.text_user_name);
      avatarImageView = (ImageView) view.findViewById(R.id.img_user_avatar);
    }
  }

}
