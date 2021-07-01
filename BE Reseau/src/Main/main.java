package Main;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import ClientSocket.MICclientSocket;
import ServerSocket.MICServerThread;

public class main {
	static String Lorem = "Eminuit autem inter humilia supergressa iam impotentia fines mediocrium delictorum nefanda Clematii cuiusdam Alexandrini nobilis mors repentina; cuius socrus cum misceri sibi generum, flagrans eius amore, non impetraret, ut ferebatur, per palatii pseudothyrum introducta, oblato pretioso reginae monili id adsecuta est, ut ad Honoratum tum comitem orientis formula missa letali omnino scelere nullo contactus idem Clematius nec hiscere nec loqui permissus occideretur.\r\n"
			+ "Soleo saepe ante oculos ponere, idque libenter crebris usurpare sermonibus, omnis nostrorum imperatorum, omnis exterarum gentium potentissimorumque populorum, omnis clarissimorum regum res gestas, cum tuis nec contentionum magnitudine nec numero proeliorum nec varietate regionum nec celeritate conficiendi nec dissimilitudine bellorum posse conferri; nec vero disiunctissimas terras citius passibus cuiusquam potuisse peragrari, quam tuis non dicam cursibus, sed victoriis lustratae sunt.\r\n"
			+ "Ibi victu recreati et quiete, postquam abierat timor, vicos opulentos adorti equestrium adventu cohortium, quae casu propinquabant, nec resistere planitie porrecta conati digressi sunt retroque concedentes omne iuventutis robur relictum in sedibus acciverunt.\r\n"
			+ "Harum trium sententiarum nulli prorsus assentior. Nec enim illa prima vera est, ut, quem ad modum in se quisque sit, sic in amicum sit animatus. Quam multa enim, quae nostra causa numquam faceremus, facimus causa amicorum! precari ab indigno, supplicare, tum acerbius in aliquem invehi insectarique vehementius, quae in nostris rebus non satis honeste, in amicorum fiunt honestissime; multaeque res sunt in quibus de suis commodis viri boni multa detrahunt detrahique patiuntur, ut iis amici potius quam ipsi fruantur.\r\n"
			+ "Post emensos insuperabilis expeditionis eventus languentibus partium animis, quas periculorum varietas fregerat et laborum, nondum tubarum cessante clangore vel milite locato per stationes hibernas, fortunae saevientis procellae tempestates alias rebus infudere communibus per multa illa et dira facinora Caesaris Galli, qui ex squalore imo miseriarum in aetatis adultae primitiis ad principale culmen insperato saltu provectus ultra terminos potestatis delatae procurrens asperitate nimia cuncta foedabat. propinquitate enim regiae stirpis gentilitateque etiam tum Constantini nominis efferebatur in fastus, si plus valuisset, ausurus hostilia in auctorem suae felicitatis, ut videbatur.\r\n"
			+ "Cuius acerbitati uxor grave accesserat incentivum, germanitate Augusti turgida supra modum, quam Hannibaliano regi fratris filio antehac Constantinus iunxerat pater, Megaera quaedam mortalis, inflammatrix saevientis adsidua, humani cruoris avida nihil mitius quam maritus; qui paulatim eruditiores facti processu temporis ad nocendum per clandestinos versutosque rumigerulos conpertis leviter addere quaedam male suetos falsa et placentia sibi discentes, adfectati regni vel artium nefandarum calumnias insontibus adfligebant.\r\n"
			+ "Ideoque fertur neminem aliquando ob haec vel similia poenae addictum oblato de more elogio revocari iussisse, quod inexorabiles quoque principes factitarunt. et exitiale hoc vitium, quod in aliis non numquam intepescit, in illo aetatis progressu effervescebat, obstinatum eius propositum accendente adulatorum cohorte.\r\n"
			+ "Utque proeliorum periti rectores primo catervas densas opponunt et fortes, deinde leves armaturas, post iaculatores ultimasque subsidiales acies, si fors adegerit, iuvaturas, ita praepositis urbanae familiae suspensae digerentibus sollicite, quos insignes faciunt virgae dexteris aptatae velut tessera data castrensi iuxta vehiculi frontem omne textrinum incedit: huic atratum coquinae iungitur ministerium, dein totum promiscue servitium cum otiosis plebeiis de vicinitate coniunctis: postrema multitudo spadonum a senibus in pueros desinens, obluridi distortaque lineamentorum conpage deformes, ut quaqua incesserit quisquam cernens mutilorum hominum agmina detestetur memoriam Samiramidis reginae illius veteris, quae teneros mares castravit omnium prima velut vim iniectans naturae, eandemque ab instituto cursu retorquens, quae inter ipsa oriundi crepundia per primigenios seminis fontes tacita quodam modo lege vias propagandae posteritatis ostendit.\r\n"
			+ "Eo adducta re per Isauriam, rege Persarum bellis finitimis inligato repellenteque a conlimitiis suis ferocissimas gentes, quae mente quadam versabili hostiliter eum saepe incessunt et in nos arma moventem aliquotiens iuvant, Nohodares quidam nomine e numero optimatum, incursare Mesopotamiam quotiens copia dederit ordinatus, explorabat nostra sollicite, si repperisset usquam locum vi subita perrupturus.\r\n"
			+ "Martinus agens illas provincias pro praefectis aerumnas innocentium graviter gemens saepeque obsecrans, ut ab omni culpa inmunibus parceretur, cum non inpetraret, minabatur se discessurum: ut saltem id metuens perquisitor malivolus tandem desineret quieti coalitos homines in aperta pericula proiectare.\r\n"
			+ "";
	
	static String IP_distante;
	
	static boolean modeServer;
	static boolean modeClient;
	static int Distant_Port;
	static int Local_Port;
	
	public static void main(String[] args){

		modeServer = false;
		modeClient = true;
		IP_distante="127.0.0.1";
		Distant_Port=50001;
		Local_Port=50001;
		
		int errorRate=2;
		int startingSequenceNumber = 0;

		
		
		
	 	try {
			System.out.println(InetAddress.getLocalHost());
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (modeServer) {
			int Distant_Server_Port = 50003;
			int Distant_Client_Port = 50004;
			int Local_Server_Port = 50001;
			int Local_Client_Port = 50002;
			
			Map<String, Integer> Ports = new HashMap<String, Integer>();
			Ports.put("Distant_Server_Port", Distant_Server_Port);
			Ports.put("Distant_Client_Port", Distant_Client_Port);
			Ports.put("Local_Server_Port", Local_Server_Port);
			Ports.put("Local_Client_Port", Distant_Client_Port);
			MICServerThread ServerThread = new MICServerThread(IP_distante, Ports, startingSequenceNumber);
			ServerThread.start();
			//System.wait(10000);
		}
		
		
		if (modeClient) {
			int Distant_Server_Port = 50001;
			int Distant_Client_Port = 50002;
			int Local_Server_Port = 50003;
			int Local_Client_Port = 50004;
			
			Map<String, Integer> Ports = new HashMap<String, Integer>();
			Ports.put("Distant_Server_Port", Distant_Server_Port);
			Ports.put("Distant_Client_Port", Distant_Client_Port);
			Ports.put("Local_Server_Port", Local_Server_Port);
			Ports.put("Local_Client_Port", Distant_Client_Port);
			MICclientSocket clientSocket = new MICclientSocket(IP_distante, Ports, errorRate, startingSequenceNumber);
			clientSocket.sendMessage(Lorem);
		}
		
	}

}
