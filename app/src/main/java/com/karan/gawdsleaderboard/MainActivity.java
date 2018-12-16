package com.karan.gawdsleaderboard;

import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    private FirebaseDatabase database;
    private DatabaseReference membersDatabaseRefference;

    ArrayList<String> users = new ArrayList<String>();
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    
    private int weeks;

    // For storing userGithubHandle and all repositories of that githubHandle
    private Map<String, ArrayList<String>> userRepos = new HashMap<String, ArrayList<String>>();
    //For storing members name from database
    private ArrayList<String> membersNames = new ArrayList<String>();
    //For storing membersGithubHandle from database
    private ArrayList<String> membersGithubHandle = new ArrayList<String>();
    // For mapping Members Name and their Github Handles
    private Map<String , String> membersData = new HashMap<String, String>();
    // For mapping a certain repository of a member with
    private Map<String, ArrayList<String>> reposWeeks = new HashMap<String, ArrayList<String>>();

    private Map<String,Integer> handleCommits = new HashMap<String, Integer>();


    public void gawdsButton(View view){
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
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        for(String userHandles : membersGithubHandle){
            getRepo(userHandles,weeks);
        }


    }

    public int getCommits(String userHandle, String repo, Integer weeks){
        int commitsPerWeek[] = new int[weeks];
        DownloadTask task2 = new DownloadTask();
        String result = null;

        try {
            result=task2.execute("https://api.github.com/repos/"+userHandle+"/"+repo+"/stats/commit_activity").get();
            Pattern p = Pattern.compile("\"total\":(.*?),");
            Matcher m = p.matcher(result);
                for(int i =0;i<weeks;i++)
                {
                    m.find();
                    if(i==0)
                    {
                        commitsPerWeek[i] = Integer.parseInt(m.group(1));
                    }
                    else
                    {
                       commitsPerWeek[i] = commitsPerWeek[i-1] + Integer.parseInt(m.group(1));
                    }
                }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return commitsPerWeek[weeks-1];
    }

    public void getRepo(String userGithubHandle,Integer weeks){
        ArrayList<String> repos = new ArrayList<String>();
        repos.clear();
        DownloadTask task = new DownloadTask();
        String result = null;
        int commits = 0;
        //EditText editText = findViewById(R.id.editText);

        try {

            result=task.execute("https://api.github.com/users/"+userGithubHandle+"/repos").get();
            Pattern p = Pattern.compile("\"name\":\"(.*?)\",\"full_name\"");
            Matcher m = p.matcher(result);
            while(m.find()){
                repos.add(m.group(1));
            }
            //editText.setText(repos.toString());
        } catch (ExecutionException e) {

            e.printStackTrace();
        } catch (InterruptedException e) {

            e.printStackTrace();
        }
        userRepos.put(userGithubHandle,repos);

        for(String repository : repos){
            commits += getCommits(userGithubHandle,repository,weeks);
        }
        handleCommits.put(userGithubHandle,commits);

    }
    public class DownloadTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            String result = "";
            URL url;
            HttpURLConnection urlConnection = null;
            try {

                url = new URL(urls[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream in = urlConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);
                int data = reader.read();
                while (data != -1) {
                    char current = (char) data;
                    result += current;
                    data = reader.read();
                }

                return result;

            } catch (Exception e) {

                e.printStackTrace();
                return "Failed";
            }
        }
        }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        mFirebaseAuth = FirebaseAuth.getInstance();
//        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
//            @Override
//            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
//                FirebaseUser user = firebaseAuth.getCurrentUser();
//            }
//        };
//        Intent intent = new Intent(Intent.ACTION_VIEW,
//                Uri.parse("https://github.com/login/oauth/authorize?scope=repo&client_id=" +clientId)); //+"&redirect_url="+redirectUrl
//        startActivity(intent);
    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//
//        Uri uri =getIntent().getData();
//        if(uri!=null&& uri.toString().startsWith(redirectUrl))
//        {
//            String code = uri.getQueryParameter("code");
//            Toast.makeText(this, "yayy", Toast.LENGTH_SHORT).show();
//        }
//
//    }
}
