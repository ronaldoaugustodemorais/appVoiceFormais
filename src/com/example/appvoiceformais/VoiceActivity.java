package com.example.appvoiceformais;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.widget.TextView;

public class VoiceActivity extends Activity implements OnInitListener, SensorEventListener
{
	
	private static final int REQUEST_CODE = 1234;
	private ListView resultList;
	private String telaAtiva;
	private Button speakButton;
	private Button backButton;
	private Button bguitarra;
	private Button bpiano;
	private Button bhouse;
	private SensorManager sManager;
	private int valor = 0;
	//private ListView list;	
	

	public void onCreate(Bundle savedInstanceState)
	{
		  super.onCreate(savedInstanceState);
		  telaAtiva = "inicial";
		  sManager = (SensorManager) getSystemService(SENSOR_SERVICE);		  
		  sManager.registerListener(this, sManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), sManager.SENSOR_DELAY_NORMAL);
		  voice_tela();
	}
	
	public void voice_tela()
	{
		setContentView(R.layout.activity_voice);
		speakButton = (Button) findViewById (R.id.speakButton);
		/*
		bguitarra = (Button) findViewById(R.id.bguitarra);
		bpiano = (Button) findViewById (R.id.bpiano);
		bhouse = (Button) findViewById (R.id.bhouse);
		bpiano.setEnabled(false);		
		bpiano.setVisibility(0);		
		bguitarra.setEnabled(false);		
		bguitarra.setVisibility(0);
		bhouse.setEnabled(false);		
		bhouse.setVisibility(0);
		*/
		//resultList = list;
		
		// Desabilitamos o botao caso nao tenha o servico
		PackageManager pm = getPackageManager();
		List<ResolveInfo> activities = pm.queryIntentActivities(
		new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH), 0);
		if (activities.size() == 0) {
		  speakButton.setEnabled(false);
		  Toast.makeText(getApplicationContext(), "Reconhecedor de voz nao encontrado", Toast.LENGTH_LONG).show();
		}
		speakButton.setOnClickListener(new OnClickListener() {
		  public void onClick(View v) {
		    startVoiceRecognitionActivity();
		  }
		});
		
		/*
		bguitarra.setOnClickListener(new View.OnClickListener()
		{
			  public void onClick(View v) {
			    tela_guit();
			    telaAtiva = "guitarra";
			  }
		});
		  
		bpiano.setOnClickListener(new View.OnClickListener()
		{
			  public void onClick(View v) {
			    tela_piano();
			    telaAtiva = "piano";
			  }
		});
		
		bhouse.setOnClickListener(new View.OnClickListener()
		{
			  public void onClick(View v) {
			    tela_house();
			    telaAtiva = "house";
			  }
		});
		*/
		
	}
	
	private void startVoiceRecognitionActivity()
	{
		  Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
		  intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
		  intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Escutando...");

		  startActivityForResult(intent, REQUEST_CODE);
	}
	
	
	public void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		  if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
		    ArrayList<String> matches = data.getStringArrayListExtra( RecognizerIntent.EXTRA_RESULTS);
		    //resultList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.list_content, matches));		    

		    for(int i = 0; i < matches.size();i++) 
		    {
		      if(matches.get(i).equalsIgnoreCase("guitarra"))
		      {
		        tela_guit();
		        break;
		      }
		      if(matches.get(i).equalsIgnoreCase("piano"))
		      {
		        tela_piano();
		        break;
		      }
		      if(matches.get(i).equalsIgnoreCase("casa"))
		      {
		    	tela_house();
		        break;
		      }
		      
		      else
		      {
		    	  tela_error();
		      }
		      
		    }
		  }
		  super.onActivityResult(requestCode, resultCode, data);		  
	}


	public void tela_error() 
	{
		  setContentView(R.layout.error);
		  backButton = (Button) findViewById(R.id.btback);
		  backButton.setOnClickListener(new OnClickListener() {
		    public void onClick(View v) {
		      voice_tela();
		    }
		  });
		}
	
	public void tela_piano() {
		  setContentView(R.layout.piano);
		  backButton = (Button) findViewById(R.id.btbackp);
		  backButton.setOnClickListener(new OnClickListener() {
		    public void onClick(View v) {
		      voice_tela();
		    }
		  });
		}
	
	public void tela_house() {
		  setContentView(R.layout.house);
		  backButton = (Button) findViewById(R.id.btbackh);
		  backButton.setOnClickListener(new OnClickListener() {
		    public void onClick(View v) {
		      voice_tela();
		    }
		  });
		}
	
	public void tela_guit() {
		  setContentView(R.layout.guitarra);
		  backButton = (Button) findViewById(R.id.btback);
		  backButton.setOnClickListener(new OnClickListener() {
		    public void onClick(View v) {
		      voice_tela();
		    }
		  });
		}

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.voice, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onInit(int status) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
		
	}

}
