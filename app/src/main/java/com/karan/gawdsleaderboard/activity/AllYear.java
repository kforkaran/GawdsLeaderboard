package com.karan.gawdsleaderboard.activity;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.karan.gawdsleaderboard.model.Commits;
import com.karan.gawdsleaderboard.model.MembersData;
import com.karan.gawdsleaderboard.R;
import com.karan.gawdsleaderboard.model.Repository;
import com.karan.gawdsleaderboard.rest.ApiClient;
import com.karan.gawdsleaderboard.rest.ApiInterface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllYear extends AppCompatActivity {

//     private EditText weeksAllYearEditText;
//     private int weeksToConsider;
    public int NoOfRepos;

    public ArrayList<String> membersGithubHandle = new ArrayList<String>();
    public ArrayList<String> membersNames = new ArrayList<String>();
    public FirebaseDatabase database;
    public DatabaseReference membersDatabaseRefference;
    public Map<String , String>  membersData = new HashMap<String, String>();

//    private Map<String, ArrayList<String>> userRepos;
//    private Map<String,Integer> handleCommits;
    private ListView resultListView;
    private ArrayList<String> containsResult = new ArrayList<String>();
    private ArrayAdapter<String> adapter;
    public Map<String, Integer> memberAndNumberOfRepos = new HashMap<String, Integer>();
    public Map<String, List<String>> githubHandleListOfRepos = new HashMap<String, List<String>>();

//    public void weeksButton(View view){
//
//        weeksToConsider = Integer.parseInt(weeksAllYearEditText.getText().toString());
//
//        if(weeksToConsider>52)
//        {
//            weeksToConsider=52;
//        }
//
//        membersGithubHandle = new ArrayList<String>();
//        membersNames = new ArrayList<String>();
//        userRepos = new HashMap<String, ArrayList<String>>();
//        handleCommits = new HashMap<String, Integer>();
//        containsResult= new ArrayList<String>();
//
//
//        for(String userHandles : membersGithubHandle){
//            getRepo(userHandles,weeksToConsider);
//        }
//
//        for(String result : membersGithubHandle){
//             containsResult.add(result);
//             containsResult.add(handleCommits.get(result).toString());
//        }
//
//    }
//    public void getCommitCountOfaRepo(String userGithubHandle, final String repo){
//
//        ApiInterface apiService = ApiClient.getClientCommits().create(ApiInterface.class);
//        Call<List<Commits>> call2 = apiService.getCommitsAuthor(userGithubHandle,repo);
//
//        call2.enqueue(new Callback<List<Commits>>() {
//            @Override
//            public void onResponse(Call<List<Commits>> call, Response<List<Commits>> response) {
//                List<Commits> commitsAuthor = response.body();
//                Toast.makeText(AllYear.this, ""+commitsAuthor.get(0).getAuthor().get(1), Toast.LENGTH_SHORT).show();
//                Toast.makeText(AllYear.this, repo+" : "+commitsAuthor.size(), Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onFailure(Call<List<Commits>> call, Throwable t) {
//
//            }
//        });
//
//    }

    public void getReposOfaMember(final String userGithubHandle){
        ApiInterface apiService = ApiClient.getClientRepo().create(ApiInterface.class);
        Call<List<Repository>> call = apiService.getRepositories(userGithubHandle);

        call.enqueue(new Callback<List<Repository>>() {
            @Override
            public void onResponse(Call<List<Repository>> call, Response<List<Repository>> response) {
                 List<Repository> repoList = response.body();
                 List<String> repoNames = new ArrayList<String>();
                 for(int i = 0; i<repoList.size();i++)
                 {
                    repoNames.add(repoList.get(i).getName());
                 }

                 githubHandleListOfRepos.put(userGithubHandle, repoNames);
                //Toast.makeText(AllYear.this, userGithubHandle+" : "+repoList.size(), Toast.LENGTH_SHORT).show();
                //Toast.makeText(AllYear.this, repoNames.toString(), Toast.LENGTH_SHORT).show();
//                for(int i = 0; i<repoList.size();i++)
//                {
//                    containsResult.add(repoList.get(i).getName());
//                }
//                adapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,containsResult);
//                resultListView.setAdapter(adapter);
                //resultListView.setAdapter(new Adapter(getApplicationContext(), repoList));
            }

            @Override
            public void onFailure(Call<List<Repository>> call, Throwable t) {
                Toast.makeText(AllYear.this, t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }
    public void go(View view){
        for(String s : membersGithubHandle) {
            for (int i = 0; i < githubHandleListOfRepos.get(s).size(); i++) {
                //getCommitCountOfaRepo(s,githubHandleListOfRepos.get(s).get(i));
                Toast.makeText(AllYear.this, githubHandleListOfRepos.get(s).get(i), Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_year);
        final Button button = (Button) findViewById(R.id.weeksAllYearButton);

        button.setVisibility(View.INVISIBLE);
//        weeksAllYearEditText = (EditText)findViewById(R.id.weeksAllYearEditText);
        resultListView = findViewById(R.id.resultListView);
        database = FirebaseDatabase.getInstance();
        membersDatabaseRefference = database.getReference("users");

        membersDatabaseRefference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    MembersData mData = ds.getValue(MembersData.class);

                    membersNames.add(mData.getName());
                    membersGithubHandle.add(mData.getHandle());
                    membersData.put(mData.getName(),mData.getHandle());
                }
                button.setVisibility(View.VISIBLE);
                for(String s : membersGithubHandle)
                {
                    getReposOfaMember(s);
                    //Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
                    //Toast.makeText(AllYear.this, githubHandleListOfRepos.get(s).toString(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

//    public void getRepo(String userGithubHandle,Integer weeks){
//        ArrayList<String> repos = new ArrayList<String>();
//        repos.clear();
//        DownloadTask task = new DownloadTask();
//        String result = null;
//        int commits = 0;
//
//        try {
//
//            result=task.execute("https://api.github.com/users/"+userGithubHandle+"/repos").get();
//            Pattern p = Pattern.compile("\"name\":\"(.*?)\",\"full_name\"");
//            Matcher m = p.matcher(result);
//            while(m.find()){
//                repos.add(m.group(1));
//            }
//        } catch (ExecutionException e) {
//
//            e.printStackTrace();
//        } catch (InterruptedException e) {
//
//            e.printStackTrace();
//        }
//           userRepos.put(userGithubHandle,repos);
//
//        for(String repository : repos){
//            commits += getCommits(userGithubHandle,repository,weeks);
//        }
//           handleCommits.put(userGithubHandle,commits);
//
//    }
//
//    public class DownloadTask extends AsyncTask<String, Void, String> {
//
//        @Override
//        protected String doInBackground(String... urls) {
//            String result = "";
//            URL url;
//            HttpURLConnection urlConnection = null;
//            try {
//
//                url = new URL(urls[0]);
//                urlConnection = (HttpURLConnection) url.openConnection();
//                InputStream in = urlConnection.getInputStream();
//                InputStreamReader reader = new InputStreamReader(in);
//                int data = reader.read();
//                while (data != -1) {
//                    char current = (char) data;
//                    result += current;
//                    data = reader.read();
//                }
//
//                return result;
//
//            } catch (Exception e) {
//
//                e.printStackTrace();
//                return "Failed";
//            }
//        }
//    }
//
//    public int getCommits(String userHandle, String repo, Integer weeks){
//        int commitsPerWeek[] = new int[weeks];
//        DownloadTask task2 = new DownloadTask();
//        String result = null;
//
//        try {
//            result=task2.execute("https://api.github.com/repos/"+userHandle+"/"+repo+"/stats/commit_activity").get();
//            Pattern p = Pattern.compile("\"total\":(.*?),");
//            Matcher m = p.matcher(result);
//            for(int i =0;i<weeks;i++)
//            {
//                m.find();
//                if(i==0)
//                {
//                    commitsPerWeek[i] = Integer.parseInt(m.group(1));
//                }
//                else
//                {
//                    commitsPerWeek[i] = commitsPerWeek[i-1] + Integer.parseInt(m.group(1));
//                }
//            }
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        return commitsPerWeek[weeks-1];
//    }
}
