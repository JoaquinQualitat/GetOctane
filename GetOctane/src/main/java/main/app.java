package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import AplicationsModules.Applications_Modules;
import Defects.Defects;
import Epicas.Epicas;
import Features.Features;
import GherkinTests.GherkinTests;
import ManualTests.ManualTests;
import RunManual.RunsManuals;
import auxiliars.Credentials;
import auxiliars.WorkSpaces;

public class app {
	
	private static String _clientId = "";
	private static String _clientSecret = "";
	
	private static String _url = "https://almoctane-eur.saas.microfocus.com";
	private static String _url_sign_in= _url+"/authentication/sign_in";
	
	private static String _url_workSpaces = _url+"/api/shared_spaces/278014/workspaces?fields=name";
	private static String _url_application_modules = _url+"/api/shared_spaces/278014/workspaces/%s/application_modules?fields=client_lock_stamp,creation_time,description,has_children,id,last_modified,name,parent,program,version_stamp&limit=10000";
	private static String _url_defects = _url+"/api/shared_spaces/278014/workspaces/%s/defects?fields=blocked,closed_on,description,name,id,creation_time,fixed_on&limit=10000";
	private static String _url_epicas = _url+"/api/shared_spaces/278014/workspaces/%s/epics?fields=name&limit=10000";
	private static String _url_features = _url+"/api/shared_spaces/278014/workspaces/%s/features?fields=creation_time,description,feature_type,id,last_modified,name,release&limit=10000";
	private static String _url_gherkinTests = _url+"/api/shared_spaces/278014/workspaces/%s/gherkin_tests?fields=creation_time,description,id,last_modified,last_runs,name,test_type&limit=10000";
	private static String _url_RunsManuals = _url+"/api/shared_spaces/278014/workspaces/%s/manual_runs?fields=creation_time,description,id,last_modified,name,test,test_name&limit=10000";
	private static String _url_ManualTests = _url+"/api/shared_spaces/278014/workspaces/%s/manual_tests?fields=creation_time,description,id,last_modified,last_runs,name&limit=10000";

	private static String _cookie_name = "LWSSO_COOKIE_KEY";
	
	public static void main( String[] args )
    {
		System.out.println("Inici");
		Client client = ClientBuilder.newClient(); 
		WebTarget target = client.target(_url_sign_in);
		
		try {
		      File myObj = new File("c:/Octane/APIKey.txt");
		      Scanner myReader = new Scanner(myObj);
		      _clientId = myReader.nextLine();
		      _clientSecret = myReader.nextLine();
		      myReader.close();
		    } catch (FileNotFoundException e) {
		      System.out.println("No se pudo acceder al fichero \"c:/Octane/APIKey.txt");
		      e.printStackTrace();
		    }
		
		//Realiza la autentificación
		Response response =
	      target
	        .request()  
	        .post(Entity.json(new Credentials(_clientId, _clientSecret)));
		
		System.out.println("Resposta autentificació: " + response.getStatus());
		
		//Si la autorización es correcta
		if(response.getStatus() == 200) {
			
			//Se guarda la cookie de autentificación
			Cookie cookie = response.getCookies().get(_cookie_name);
			
			WebTarget target_workSpaces = client.target(_url_workSpaces);
			
			//Obtiene los workspaces
			WorkSpaces workSpaces = target_workSpaces
			        .request(MediaType.APPLICATION_JSON)
			        .cookie(cookie)
			        .get(WorkSpaces.class);
			
			System.out.println("WorkSpaces: " + workSpaces.total_count);

			

			for (int i=0; i<workSpaces.total_count; i++) 
			{
			      System.out.println(workSpaces.data.get(i).id + " " + workSpaces.data.get(i).name );
			      
			      
			      //Obtiene los Applications_Modules
			      String _url_application_modules_new= String.format(_url_application_modules,workSpaces.data.get(i).id);
			      
			      System.out.println(_url_application_modules_new);
			      
			      WebTarget target_application_modules = client.target(_url_application_modules_new);
			      
			      Applications_Modules applications_modules = target_application_modules
					        .request(MediaType.APPLICATION_JSON)
					        .cookie(cookie)
					        .get(Applications_Modules.class);
					
			      System.out.println("                Applications_Modules: " + applications_modules.total_count);
			      
			      for (int ii=0; ii<applications_modules.total_count; ii++) 
				  {
			    	  System.out.println("                    Application_module: "+applications_modules.data.get(ii).name);
				  }
			      
			      //Obtiene los Defects
			      String _url_defects_new = String.format(_url_defects,workSpaces.data.get(i).id);
			      
			      System.out.println(_url_defects_new);
			      WebTarget target_defects = client.target(_url_defects_new);
			      
			      Defects defects = target_defects
					        .request(MediaType.APPLICATION_JSON)
					        .cookie(cookie)
					        .get(Defects.class);
					
			      System.out.println("                Defects: " + defects.total_count);
			      
			      for (int ii=0; ii<defects.total_count; ii++) 
				  {
			    	  System.out.println("                    Defect: "+defects.data.get(ii).name);
				  }
			      
			     //Obtiene las epicas
			      String _url_epica_new = String.format(_url_epicas,workSpaces.data.get(i).id);
			      
			      System.out.println(_url_epica_new);
			      WebTarget target_epicas = client.target(_url_epica_new);
			      
			      Epicas epicas = target_epicas
					        .request(MediaType.APPLICATION_JSON)
					        .cookie(cookie)
					        .get(Epicas.class);
					
			      System.out.println("                Epicas: " + epicas.total_count);
			      
			      for (int ii=0; ii<epicas.total_count; ii++) 
				  {
			    	  System.out.println("                    Epica: "+epicas.data.get(ii).name);
				  }
			      
			    //Obtiene las features
			      String _url_features_new = String.format(_url_features,workSpaces.data.get(i).id);
			      
			      System.out.println(_url_features_new);
			      WebTarget target_features = client.target(_url_features_new);
			      
			      Features features = target_features
					        .request(MediaType.APPLICATION_JSON)
					        .cookie(cookie)
					        .get(Features.class);
					
			      System.out.println("                Features: " + features.total_count);
			      
			      for (int ii=0; ii<features.total_count; ii++) 
				  {
			    	  System.out.println("                    Features: "+features.data.get(ii).name);
				  }
			      
			    //Obtiene las GherkinTests
			      String _url_GherkinTests_new = String.format(_url_gherkinTests,workSpaces.data.get(i).id);
			      
			      System.out.println(_url_GherkinTests_new);
			      WebTarget target_GherkinTests = client.target(_url_GherkinTests_new);
			      
			      GherkinTests gherkinTests = target_GherkinTests
					        .request(MediaType.APPLICATION_JSON)
					        .cookie(cookie)
					        .get(GherkinTests.class);
					
			      System.out.println("                GherkinTests: " + gherkinTests.total_count);
			      
			      for (int ii=0; ii<gherkinTests.total_count; ii++) 
				  {
			    	  System.out.println("                    GherkinTest: "+features.data.get(ii).name);
				  }
			      
			    //Obtiene las RunsManuals
			      String _url_RunsManuals_new = String.format(_url_RunsManuals,workSpaces.data.get(i).id);
			      
			      System.out.println(_url_RunsManuals_new);
			      WebTarget target_RunsManuals = client.target(_url_RunsManuals_new);
			      
			      RunsManuals runsManuals = target_RunsManuals
					        .request(MediaType.APPLICATION_JSON)
					        .cookie(cookie)
					        .get(RunsManuals.class);
					
			      System.out.println("                RunsManuals: " + runsManuals.total_count);
			      
			      for (int ii=0; ii<runsManuals.total_count; ii++) 
				  {
			    	  System.out.println("                    RunsManuals: "+runsManuals.data.get(ii).name);
				  }
			      
			    //Obtiene las ManualTests
			      String _url_ManualTests_new = String.format(_url_ManualTests,workSpaces.data.get(i).id);
			      
			      System.out.println(_url_RunsManuals_new);
			      WebTarget target_ManualTests = client.target(_url_ManualTests_new);
			      
			      ManualTests manualTests = target_ManualTests
					        .request(MediaType.APPLICATION_JSON)
					        .cookie(cookie)
					        .get(ManualTests.class);
					
			      System.out.println("                ManualTests: " + manualTests.total_count);
			      
			      for (int ii=0; ii<manualTests.total_count; ii++) 
				  {
			    	  System.out.println("                    ManualTest: "+manualTests.data.get(ii).name);
				  }
			      
			    //Obtiene las ManualTests
			      String _url_ManualTests_new = String.format(_url_ManualTests,workSpaces.data.get(i).id);
			      
			      System.out.println(_url_RunsManuals_new);
			      WebTarget target_ManualTests = client.target(_url_ManualTests_new);
			      
			      ManualTests manualTests = target_ManualTests
					        .request(MediaType.APPLICATION_JSON)
					        .cookie(cookie)
					        .get(ManualTests.class);
					
			      System.out.println("                ManualTests: " + manualTests.total_count);
			      
			      for (int ii=0; ii<manualTests.total_count; ii++) 
				  {
			    	  System.out.println("                    ManualTest: "+manualTests.data.get(ii).name);
				  }
			    
			}
		}

    }
}
