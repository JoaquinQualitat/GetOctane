//package main;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import javax.ws.rs.client.Client;
//import javax.ws.rs.client.ClientBuilder;
//import javax.ws.rs.client.Entity;
//import javax.ws.rs.client.Invocation;
//import javax.ws.rs.client.Invocation.Builder;
//import javax.ws.rs.client.WebTarget;
//import javax.ws.rs.core.Cookie;
//import javax.ws.rs.core.GenericType;
//import javax.ws.rs.core.MediaType;
//import javax.ws.rs.core.NewCookie;
//import javax.ws.rs.core.Response;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//
//public class Octane {
//	
//	private String _clientId="Oqual_API_admin_6vw0mgp484rvdu02wk716m7ly";
//	private String _clientSecret="%19288185437112959Q";
//	private String sharedSpaceId="278014";
//    private String workspaceId="7001";
//	
//	private String _cookie_name = "LWSSO_COOKIE_KEY";
//	private String _tech_preview = "ALM_OCTANE_TECH_PREVIEW";
//	
//	private String _url = "https://almoctane-eur.saas.microfocus.com";
//	private String _url_sign_in= _url+"/authentication/sign_in";
//	private String _url_gherkin_test= _url+"/api/shared_spaces/%s/workspaces/%s/gherkin_tests/%s";
//	private String _url_releases= _url+"/api/shared_spaces/%s/workspaces/%s/releases";
//	private String _url_runGherkin= _url+"/api/shared_spaces/%s/workspaces/%s/manual_runs";
//
//	
//	public Boolean SendResult(String name, String number, Boolean passed) {
//		
//		//Sol·licitud d'autenticació
//		Client client = ClientBuilder.newClient(); 
//
//		WebTarget target = client.target(_url_sign_in);
//		
//		Response response =
//		      target
//		        .request()  
//		        .post(Entity.json(new Credentials(_clientId, _clientSecret)));
//		
//		System.out.println("Resposta autentificació: " + response.getStatus());
//		
//		//Si funciona l'autenticació 
//		if(response.getStatus() == 200) {
//			
//			//Obté la galeta d'autorizació
//			Cookie cookie = response.getCookies().get(_cookie_name);
//			
//			//Formata la url per obtenir el test Gherkin
//			String url_target = String.format(_url_gherkin_test, sharedSpaceId, workspaceId, number);
//			
//			WebTarget target_test = client.target(url_target);
//			
//			//Obté el test Gherkin
//			TestGherkin testGherkin = target_test
//			        .request(MediaType.APPLICATION_JSON)
//			        .cookie(cookie)
//			        .get(TestGherkin.class);
//			
//			//Formata la url per obtenir les releases
//			String url_releases= String.format(_url_releases, sharedSpaceId, workspaceId, number);
//
//			WebTarget target_releases = client.target(url_releases);
//			
//			Releases response_releases = target_releases
//			        .request(MediaType.APPLICATION_JSON)
//			        .cookie(cookie)
//			        .get(Releases.class);
//		
//			//Selecciona l'última release
//			Release release = response_releases.data.get(response_releases.total_count-1);
//			
//			System.out.println(release.id);
//			
//			//Crea l'objecte status. A la classe se li assigna el valor necessari.
//			Status status = new Status(passed);
//			
//			RunGherkin runGherkin = new RunGherkin();
//			runGherkin.name=name;
//			runGherkin.native_status=status;
//			runGherkin.release=release;
//			runGherkin.test=testGherkin;
//			
//			RunsGherkin runsGherkin = new RunsGherkin();
//			runsGherkin.data = new ArrayList<RunGherkin>();
//			runsGherkin.data.add(runGherkin);
//						
//			//Mostra el json construït a la consola
//			ObjectMapper mapper = new ObjectMapper();
//			try {
//		             String json = mapper.writeValueAsString(runsGherkin);
//		             System.out.println("ResultingJSONstring = " + json);
//		             //System.out.println(json);
//		         } catch (JsonProcessingException e) {
//		             e.printStackTrace();
//		         }
//			System.out.println(Entity.json(runsGherkin));
//			
//			//Formata la url per afegir el run
//			String url_runGherkin = String.format(_url_runGherkin, sharedSpaceId, workspaceId);
//			WebTarget target_runGherkin = client.target(url_runGherkin);
//			
//			//Afegeix el run Octane
//			Response response_runGherkin = target_runGherkin
//			        .request(MediaType.APPLICATION_JSON)
//			        .cookie(cookie)
//			        .post(Entity.json(runsGherkin));
//			
//			System.out.println(response_runGherkin.toString());
//		
//			return true;
//		}
//		else
//		{
//			return false;
//		}
//	}
//
//}
