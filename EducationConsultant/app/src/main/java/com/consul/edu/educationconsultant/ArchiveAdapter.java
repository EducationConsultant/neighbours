package com.consul.edu.educationconsultant;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Danilo
 * Adapter for Archive view of questions
 */

public class ArchiveAdapter extends RecyclerView.Adapter<ArchiveAdapter.MyViewHolder> {

    private List<Question> questionList;
    private Context mContext;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, answered, correctAns;
        RelativeLayout parentLayout;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            answered = (TextView) view.findViewById(R.id.answered);
            correctAns = (TextView) view.findViewById(R.id.correct_ans);
            parentLayout = view.findViewById(R.id.archive_layout);

            /*String ansStr = answered.getText().toString();//.trim();
            String corrStr = correctAns.getText().toString();//.trim();

            Log.d("CORR", ansStr + " ans");
            Log.d("CORR", corrStr + " corr");
            // if question answered correctly make it green, else red
            if (ansStr.equals(corrStr)){
                view.setBackgroundColor(Color.parseColor("#64bc72")); // green
            } else {
                view.setBackgroundColor(Color.parseColor("#ce6767")); // red
            }*/
        }
    }


    public ArchiveAdapter(Context context, List<Question> questionList) {
        this.questionList = questionList;
        mContext = context;
    }

    @Override
    public ArchiveAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.archive_row, parent, false);

        return new ArchiveAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ArchiveAdapter.MyViewHolder holder, final int position) {
        Question question = questionList.get(position);
        holder.title.setText(question.getTitle());
        holder.answered.setText(question.getAnswered());
        holder.correctAns.setText(question.getCorrectAns());

        if (question.getAnswered().equals(question.getCorrectAns())){
            holder.answered.setBackgroundColor(Color.parseColor("#64bc72")); // green
        } else {
            holder.answered.setBackgroundColor(Color.parseColor("#ce6767")); // red
        }

        /*holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, "Clicked", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(mContext, DetailsActivity.class);
                intent.putExtra("title", questionList.get(position).getTitle());
                intent.putExtra("username", questionList.get(position).getUsername());
                intent.putExtra("description", questionList.get(position).getDescription());
                intent.putExtra("category", questionList.get(position).getCategory());
                intent.putExtra("answer1", questionList.get(position).getAnswer1());
                intent.putExtra("answer2", questionList.get(position).getAnswer2());
                intent.putExtra("answer3", questionList.get(position).getAnswer3());
                intent.putExtra("answer4", questionList.get(position).getAnswer4());
                intent.putExtra("eduLevel", questionList.get(position).getEduLevel());
                intent.putExtra("correctAns", questionList.get(position).getCorrectAns());
                intent.putExtra("answered", questionList.get(position).getAnswered());

                mContext.startActivity(intent);
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return questionList.size();
    }

}

