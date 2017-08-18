package dcsoft.somsipinzano;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javadz.beanutils.BeanComparator;

interface FirebaseHelperEseguiAlScaricamentoCompletato {
    void esegui();
}

class FirebaseHelper {
    // variabili private

    private final String TAG = getClass().getSimpleName();
    private static FirebaseHelper firebaseHelperCondiviso;
    private Query queryCategoria;
    private Query queryImmaginePdi;
    private Query queryPdi;
    private Query queryRaggruppamentoPdi;
    private ArrayList<Categoria> listaCategoria;
    private ArrayList<ImmaginePdi> listaImmaginePdi;
    private ArrayList<Pdi> listaPdi;
    private ArrayList<RaggruppamentoPdi> listaRaggruppamentoPdi;
    private Boolean scaricamentoRiuscito;

    // variabili pubbliche

    FirebaseHelperEseguiAlScaricamentoCompletato eseguiAlScaricamentoCompletato;

    //region Metodi privati

    // costruttore privato per costringere l'uso questa classe solo come singleton
    private FirebaseHelper() {
        super();

        scaricamentoRiuscito = false;

        FirebaseDatabase.getInstance().setPersistenceEnabled(true);

        queryCategoria         = FirebaseDatabase.getInstance().getReference().child("CATEGORIA");
        queryImmaginePdi       = FirebaseDatabase.getInstance().getReference().child("IMMAGINE_PDI");
        queryPdi               = FirebaseDatabase.getInstance().getReference().child("PDI");
        queryRaggruppamentoPdi = FirebaseDatabase.getInstance().getReference().child("RAGGRUPPAMENTO_PDI");

        queryCategoria.keepSynced(true);
        queryImmaginePdi.keepSynced(true);
        queryPdi.keepSynced(true);
        queryRaggruppamentoPdi.keepSynced(true);
    }

    private void gestisciErroreScaricamentoIniziale() {
        scaricamentoRiuscito = false;

        lanciaEventoScaricamentoCompletato();
    }

    private void lanciaEventoScaricamentoCompletato() {
        if (eseguiAlScaricamentoCompletato != null) {
            eseguiAlScaricamentoCompletato.esegui();
        }
    }

    private void scaricaImmaginePdi() {
        Log.d("DEBUGAPP", TAG + " [scaricaImmaginePdi]");

        queryImmaginePdi.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listaImmaginePdi = new ArrayList<ImmaginePdi>();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String idImmaginePdi       = (String) snapshot.child("idImmaginePdi").getValue();
                    String idImmaginePdi_idPdi = (String) snapshot.child("idImmaginePdi_idPdi").getValue();
                    String ordinamento         = (String) snapshot.child("ordinamento").getValue();
                    String url                 = (String) snapshot.child("url").getValue();

                    ImmaginePdi immaginePdi = new ImmaginePdi(
                            idImmaginePdi       == null || idImmaginePdi.equals("<null>")       ? null : Integer.valueOf(idImmaginePdi),
                            idImmaginePdi_idPdi == null || idImmaginePdi_idPdi.equals("<null>") ? null : Integer.valueOf(idImmaginePdi_idPdi),
                            ordinamento         == null || ordinamento.equals("<null>")         ? null : Integer.valueOf(ordinamento),
                            url                 == null || url.equals("<null>")                 ? null : url);

                    listaImmaginePdi.add(immaginePdi);
                }

                Log.d("DEBUGAPP", TAG + " [scaricaImmaginePdi - onDataChange] listaImmaginePdi: " + listaImmaginePdi);

                scaricaPdi();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("DEBUGAPP", TAG + "[scaricaImmaginePdi - onCancelled] databaseError: " + databaseError.toString());

                gestisciErroreScaricamentoIniziale();
            }
        });
    }

    private void scaricaPdi() {
        Log.d("DEBUGAPP", TAG + " [scaricaPdi]");

        queryPdi.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listaPdi = new ArrayList<Pdi>();

                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    String idPdi                       = (String) snapshot.child("idPdi").getValue();
                    String idPdi_idCategoria           = (String) snapshot.child("idPdi_idCategoria").getValue();
                    String idPdi_idRaggruppamento      = (String) snapshot.child("idPdi_idRaggruppamento").getValue();
                    String ordinamento                 = (String) snapshot.child("ordinamento").getValue();
                    String titoloItaliano              = (String) snapshot.child("titoloItaliano").getValue();
                    String titoloInglese               = (String) snapshot.child("titoloInglese").getValue();
                    String titoloTedesco               = (String) snapshot.child("titoloTedesco").getValue();
                    String titoloFrancese              = (String) snapshot.child("titoloFrancese").getValue();
                    String descrizioneItaliano         = (String) snapshot.child("descrizioneItaliano").getValue();
                    String descrizioneInglese          = (String) snapshot.child("descrizioneInglese").getValue();
                    String descrizioneTedesco          = (String) snapshot.child("descrizioneTedesco").getValue();
                    String descrizioneFrancese         = (String) snapshot.child("descrizioneFrancese").getValue();
                    String citta                       = (String) snapshot.child("citta").getValue();
                    String via                         = (String) snapshot.child("via").getValue();
                    String numeroCivico                = (String) snapshot.child("numeroCivico").getValue();
                    String interno                     = (String) snapshot.child("interno").getValue();
                    String cap                         = (String) snapshot.child("cap").getValue();
                    String latitudine                  = (String) snapshot.child("latitudine").getValue();
                    String longitudine                 = (String) snapshot.child("longitudine").getValue();
                    String telefono                    = (String) snapshot.child("telefono").getValue();
                    String fax                         = (String) snapshot.child("fax").getValue();
                    String cellulare                   = (String) snapshot.child("cellulare").getValue();
                    String email                       = (String) snapshot.child("email").getValue();
                    String titoloLink1GenericoItaliano = (String) snapshot.child("titoloLink1GenericoItaliano").getValue();
                    String titoloLink1GenericoInglese  = (String) snapshot.child("titoloLink1GenericoInglese").getValue();
                    String titoloLink1GenericoTedesco  = (String) snapshot.child("titoloLink1GenericoTedesco").getValue();
                    String titoloLink1GenericoFrancese = (String) snapshot.child("titoloLink1GenericoFrancese").getValue();
                    String linkGenerico1               = (String) snapshot.child("linkGenerico1").getValue();
                    String titoloLink2GenericoItaliano = (String) snapshot.child("titoloLink2GenericoItaliano").getValue();
                    String titoloLink2GenericoInglese  = (String) snapshot.child("titoloLink2GenericoInglese").getValue();
                    String titoloLink2GenericoTedesco  = (String) snapshot.child("titoloLink2GenericoTedesco").getValue();
                    String titoloLink2GenericoFrancese = (String) snapshot.child("titoloLink2GenericoFrancese").getValue();
                    String linkGenerico2               = (String) snapshot.child("linkGenerico2").getValue();
                    String titoloLink3GenericoItaliano = (String) snapshot.child("titoloLink3GenericoItaliano").getValue();
                    String titoloLink3GenericoInglese  = (String) snapshot.child("titoloLink3GenericoInglese").getValue();
                    String titoloLink3GenericoTedesco  = (String) snapshot.child("titoloLink3GenericoTedesco").getValue();
                    String titoloLink3GenericoFrancese = (String) snapshot.child("titoloLink3GenericoFrancese").getValue();
                    String linkGenerico3               = (String) snapshot.child("linkGenerico3").getValue();
                    String titoloLink4GenericoItaliano = (String) snapshot.child("titoloLink4GenericoItaliano").getValue();
                    String titoloLink4GenericoInglese  = (String) snapshot.child("titoloLink4GenericoInglese").getValue();
                    String titoloLink4GenericoTedesco  = (String) snapshot.child("titoloLink4GenericoTedesco").getValue();
                    String titoloLink4GenericoFrancese = (String) snapshot.child("titoloLink4GenericoFrancese").getValue();
                    String linkGenerico4               = (String) snapshot.child("linkGenerico4").getValue();
                    String fileTracciaGps              = (String) snapshot.child("fileTracciaGps").getValue();
                    String urlTracciaGps               = (String) snapshot.child("urlTracciaGps").getValue();

                    Pdi pdi = new Pdi(
                            idPdi                       == null || idPdi.equals("<null>")                       ? null : Integer.valueOf(idPdi),
                            idPdi_idCategoria           == null || idPdi_idCategoria.equals("<null>")           ? null : Integer.valueOf(idPdi_idCategoria),
                            idPdi_idRaggruppamento      == null || idPdi_idRaggruppamento.equals("<null>")      ? null : Integer.valueOf(idPdi_idRaggruppamento),
                            ordinamento                 == null || ordinamento.equals("<null>")                 ? null : Integer.valueOf(ordinamento),
                            titoloItaliano              == null || titoloItaliano.equals("<null>")              ? null : titoloItaliano,
                            titoloInglese               == null || titoloInglese.equals("<null>")               ? null : titoloInglese,
                            titoloTedesco               == null || titoloTedesco.equals("<null>")               ? null : titoloTedesco,
                            titoloFrancese              == null || titoloFrancese.equals("<null>")              ? null : titoloFrancese,
                            descrizioneItaliano         == null || descrizioneItaliano.equals("<null>")         ? null : descrizioneItaliano,
                            descrizioneInglese          == null || descrizioneInglese.equals("<null>")          ? null : descrizioneInglese,
                            descrizioneTedesco          == null || descrizioneTedesco.equals("<null>")          ? null : descrizioneTedesco,
                            descrizioneFrancese         == null || descrizioneFrancese.equals("<null>")         ? null : descrizioneFrancese,
                            citta                       == null || citta.equals("<null>")                       ? null : citta,
                            via                         == null || via.equals("<null>")                         ? null : via,
                            numeroCivico                == null || numeroCivico.equals("<null>")                ? null : Integer.valueOf(numeroCivico),
                            interno                     == null || interno.equals("<null>")                     ? null : interno,
                            cap                         == null || cap.equals("<null>")                         ? null : Integer.valueOf(cap),
                            latitudine                  == null || latitudine.equals("<null>")                  ? null : Double.valueOf(latitudine),
                            longitudine                 == null || longitudine.equals("<null>")                 ? null : Double.valueOf(longitudine),
                            telefono                    == null || telefono.equals("<null>")                    ? null : telefono,
                            fax                         == null || fax.equals("<null>")                         ? null : fax,
                            cellulare                   == null || cellulare.equals("<null>")                   ? null : cellulare,
                            email                       == null || email.equals("<null>")                       ? null : email,
                            titoloLink1GenericoItaliano == null || titoloLink1GenericoItaliano.equals("<null>") ? null : titoloLink1GenericoItaliano,
                            titoloLink1GenericoInglese  == null || titoloLink1GenericoInglese.equals("<null>")  ? null : titoloLink1GenericoInglese,
                            titoloLink1GenericoTedesco  == null || titoloLink1GenericoTedesco.equals("<null>")  ? null : titoloLink1GenericoTedesco,
                            titoloLink1GenericoFrancese == null || titoloLink1GenericoFrancese.equals("<null>") ? null : titoloLink1GenericoFrancese,
                            linkGenerico1               == null || linkGenerico1.equals("<null>")               ? null : linkGenerico1,
                            titoloLink2GenericoItaliano == null || titoloLink2GenericoItaliano.equals("<null>") ? null : titoloLink2GenericoItaliano,
                            titoloLink2GenericoInglese  == null || titoloLink2GenericoInglese.equals("<null>")  ? null : titoloLink2GenericoInglese,
                            titoloLink2GenericoTedesco  == null || titoloLink2GenericoTedesco.equals("<null>")  ? null : titoloLink2GenericoTedesco,
                            titoloLink2GenericoFrancese == null || titoloLink2GenericoFrancese.equals("<null>") ? null : titoloLink2GenericoFrancese,
                            linkGenerico2               == null || linkGenerico2.equals("<null>")               ? null : linkGenerico2,
                            titoloLink3GenericoItaliano == null || titoloLink3GenericoItaliano.equals("<null>") ? null : titoloLink3GenericoItaliano,
                            titoloLink3GenericoInglese  == null || titoloLink3GenericoInglese.equals("<null>")  ? null : titoloLink3GenericoInglese,
                            titoloLink3GenericoTedesco  == null || titoloLink3GenericoTedesco.equals("<null>")  ? null : titoloLink3GenericoTedesco,
                            titoloLink3GenericoFrancese == null || titoloLink3GenericoFrancese.equals("<null>") ? null : titoloLink3GenericoFrancese,
                            linkGenerico2               == null || linkGenerico3.equals("<null>")               ? null : linkGenerico3,
                            titoloLink4GenericoItaliano == null || titoloLink4GenericoItaliano.equals("<null>") ? null : titoloLink4GenericoItaliano,
                            titoloLink4GenericoInglese  == null || titoloLink4GenericoInglese.equals("<null>")  ? null : titoloLink4GenericoInglese,
                            titoloLink4GenericoTedesco  == null || titoloLink4GenericoTedesco.equals("<null>")  ? null : titoloLink4GenericoTedesco,
                            titoloLink4GenericoFrancese == null || titoloLink4GenericoFrancese.equals("<null>") ? null : titoloLink4GenericoFrancese,
                            linkGenerico4               == null || linkGenerico4.equals("<null>")               ? null : linkGenerico4,
                            fileTracciaGps              == null || fileTracciaGps.equals("<null>")              ? null : fileTracciaGps,
                            urlTracciaGps               == null || urlTracciaGps.equals("<null>")               ? null : urlTracciaGps);

                    listaPdi.add(pdi);
                }

                Log.d("DEBUGAPP", TAG + " [scaricaPdi - onDataChange] listaPdi: " + listaPdi);

                scaricaRaggruppamentoPdi();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("DEBUGAPP", TAG + "[scaricaPdi - onCancelled] databaseError: " + databaseError.toString());

                gestisciErroreScaricamentoIniziale();
            }
        });
    }

    private void scaricaRaggruppamentoPdi() {
        Log.d("DEBUGAPP", TAG + " [scaricaRaggruppamentoPdi]");

        queryRaggruppamentoPdi.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listaRaggruppamentoPdi = new ArrayList<RaggruppamentoPdi>();

                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    String idRaggruppamentoPdi        = (String) snapshot.child("idRaggruppamentoPdi").getValue();
                    String ordinamento                = (String) snapshot.child("ordinamento").getValue();
                    String nomeRaggruppamentoItaliano = (String) snapshot.child("nomeRaggruppamentoItaliano").getValue();
                    String nomeRaggruppamentoInglese  = (String) snapshot.child("nomeRaggruppamentoInglese").getValue();
                    String nomeRaggruppamentoTedesco  = (String) snapshot.child("nomeRaggruppamentoTedesco").getValue();
                    String nomeRaggruppamentoFrancese = (String) snapshot.child("nomeRaggruppamentoFrancese").getValue();

                    RaggruppamentoPdi raggruppamentoPdi = new RaggruppamentoPdi(
                            idRaggruppamentoPdi        == null || idRaggruppamentoPdi.equals("<null>")        ? null : Integer.valueOf(idRaggruppamentoPdi),
                            ordinamento                == null || ordinamento.equals("<null>")                ? null : Integer.valueOf(ordinamento),
                            nomeRaggruppamentoItaliano == null || nomeRaggruppamentoItaliano.equals("<null>") ? null : nomeRaggruppamentoItaliano,
                            nomeRaggruppamentoInglese  == null || nomeRaggruppamentoInglese.equals("<null>")  ? null : nomeRaggruppamentoInglese,
                            nomeRaggruppamentoTedesco  == null || nomeRaggruppamentoTedesco.equals("<null>")  ? null : nomeRaggruppamentoTedesco,
                            nomeRaggruppamentoFrancese == null || nomeRaggruppamentoFrancese.equals("<null>") ? null : nomeRaggruppamentoFrancese);

                    listaRaggruppamentoPdi.add(raggruppamentoPdi);
                }

                Log.d("DEBUGAPP", TAG + " [scaricaRaggruppamentoPdi - onDataChange] listaRaggruppamentoPdi: " + listaRaggruppamentoPdi);

                scaricamentoRiuscito = true;

                lanciaEventoScaricamentoCompletato();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("DEBUGAPP", TAG + "[scaricaRaggruppamentoPdi - onCancelled] databaseError: " + databaseError.toString());

                gestisciErroreScaricamentoIniziale();
            }
        });
    }

    //endregion

    //region Metodi pubblici

    // singleton
    static synchronized FirebaseHelper dammiFirebaseHelperCondiviso() {
        if (firebaseHelperCondiviso == null) {
            firebaseHelperCondiviso = new FirebaseHelper();
        }

        return firebaseHelperCondiviso;
    }

    void iniziaScaricareDb() {
        Log.d("DEBUGAPP", TAG + " [iniziaScaricareDb]");

        queryCategoria.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listaCategoria = new ArrayList<Categoria>();

                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    String idCategoria           = (String) snapshot.child("idCategoria").getValue();
                    String ordinamento           = (String) snapshot.child("ordinamento").getValue();
                    String nomeItaliano          = (String) snapshot.child("nomeItaliano").getValue();
                    String nomeInglese           = (String) snapshot.child("nomeInglese").getValue();
                    String nomeTedesco           = (String) snapshot.child("nomeTedesco").getValue();
                    String nomeFrancese          = (String) snapshot.child("nomeFrancese").getValue();
                    String descrizioneItaliano   = (String) snapshot.child("descrizioneItaliano").getValue();
                    String descrizioneInglese    = (String) snapshot.child("descrizioneInglese").getValue();
                    String descrizioneTedesco    = (String) snapshot.child("descrizioneTedesco").getValue();
                    String descrizioneFrancese   = (String) snapshot.child("descrizioneFrancese").getValue();
                    String fileImmagine          = (String) snapshot.child("fileImmagine").getValue();
                    String fileImmagineCopertina = (String) snapshot.child("fileImmagineCopertina").getValue();
                    String filePin               = (String) snapshot.child("filePin").getValue();

                    Categoria categoria = new Categoria(
                            idCategoria           == null || idCategoria.equals("<null>")           ? null : Integer.valueOf(idCategoria),
                            ordinamento           == null || ordinamento.equals("<null>")           ? null : Integer.valueOf(ordinamento),
                            nomeItaliano          == null || nomeItaliano.equals("<null>")          ? null : nomeItaliano,
                            nomeInglese           == null || nomeInglese.equals("<null>")           ? null : nomeInglese,
                            nomeTedesco           == null || nomeTedesco.equals("<null>")           ? null : nomeTedesco,
                            nomeFrancese          == null || nomeFrancese.equals("<null>")          ? null : nomeFrancese,
                            descrizioneItaliano   == null || descrizioneItaliano.equals("<null>")   ? null : descrizioneItaliano,
                            descrizioneInglese    == null || descrizioneInglese.equals("<null>")    ? null : descrizioneInglese,
                            descrizioneTedesco    == null || descrizioneTedesco.equals("<null>")    ? null : descrizioneTedesco,
                            descrizioneFrancese   == null || descrizioneFrancese.equals("<null>")   ? null : descrizioneFrancese,
                            fileImmagine          == null || fileImmagine.equals("<null>")          ? null : fileImmagine,
                            fileImmagineCopertina == null || fileImmagineCopertina.equals("<null>") ? null : fileImmagineCopertina,
                            filePin               == null || filePin.equals("<null>")               ? null : filePin);

                    listaCategoria.add(categoria);
                }

                Log.d("DEBUGAPP", TAG + " [iniziaScaricareDb - onDataChange] listaCategoria: " + listaCategoria);

                scaricaImmaginePdi();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("DEBUGAPP", TAG + "[iniziaScaricareDb - onCancelled] databaseError: " + databaseError.toString());

                gestisciErroreScaricamentoIniziale();
            }
        });
    }

    ArrayList<Categoria> dammiCategorie() {
        return listaCategoria;
    }

    Categoria dammiCategoria(Integer idCategoria) {
        Categoria categoria = null;

        for (Categoria c: listaCategoria) {
            if (c.getIdCategoria() != null && idCategoria != null && c.getIdCategoria().intValue() == idCategoria.intValue()) {
                categoria = c;

                break;
            }
        }

        return categoria;
    }

    ArrayList<ImmaginePdi> dammiImmaginiPdiPerPdi(Integer idPdi) {
        ArrayList<ImmaginePdi> list = new ArrayList<>();

        for (ImmaginePdi immPdi: listaImmaginePdi) {
            if (immPdi.getIdImmaginePdi_idPdi() != null && idPdi != null && immPdi.getIdImmaginePdi_idPdi().intValue() == idPdi.intValue()) {
                list.add(immPdi);
            }
        }

        Collections.sort(list, new BeanComparator("ordinamento"));

        return list;
    }

    ArrayList<Pdi> dammiPdi() {
        return listaPdi;
    }

    ArrayList<Pdi> dammiPdiPerCategoria(Integer idCategoria) {
        ArrayList<Pdi> list = new ArrayList<>();

        for (Pdi p: listaPdi) {
            if (p.getIdPdi_idCategoria() != null && idCategoria != null && p.getIdPdi_idCategoria().intValue() == idCategoria.intValue()) {
                list.add(p);
            }
        }

        Collections.sort(list, new BeanComparator("ordinamento"));

        return list;
    }

    ArrayList<Pdi> dammiPdiPerRaggruppamento(Integer idRaggruppamentoPdi) {
        ArrayList<Pdi> list = new ArrayList<>();

        for (Pdi p: listaPdi) {
            if (p.getIdPdi_idRaggruppamento() != null && idRaggruppamentoPdi != null && p.getIdPdi_idRaggruppamento().intValue() == idRaggruppamentoPdi.intValue()) {
                list.add(p);
            }
        }

        Collections.sort(list, new BeanComparator("ordinamento"));

        return list;
    }

    RaggruppamentoPdi dammiRaggruppamentoPdi(Integer idRaggruppamentoPdi) {
        RaggruppamentoPdi raggruppamentoPdi = null;

        for (RaggruppamentoPdi raggPdi: listaRaggruppamentoPdi) {
            if (raggPdi.getIdRaggruppamentoPdi() != null && idRaggruppamentoPdi != null && raggPdi.getIdRaggruppamentoPdi().intValue() == idRaggruppamentoPdi.intValue()) {
                raggruppamentoPdi = raggPdi;

                break;
            }
        }

        return raggruppamentoPdi;
    }

    ArrayList<RaggruppamentoPdi> dammiRaggruppamentiPdiPerCategoria(Integer idCategoria) {
        Set<Integer> idRaggruppanetiPerCategoria = new HashSet<>();

        for (Pdi p: listaPdi) {
            if (p.getIdPdi_idCategoria() != null && idCategoria != null && p.getIdPdi_idCategoria().intValue() == idCategoria.intValue()) {
                if (p.getIdPdi_idRaggruppamento() != null) {
                    idRaggruppanetiPerCategoria.add(p.getIdPdi_idRaggruppamento());
                }
            }
        }

        ArrayList<RaggruppamentoPdi> list = new ArrayList<>();

        for (Integer idRaggruppamento : idRaggruppanetiPerCategoria) {
            for (RaggruppamentoPdi raggPdi: listaRaggruppamentoPdi) {
                if (raggPdi.getIdRaggruppamentoPdi() != null && raggPdi.getIdRaggruppamentoPdi().intValue() == idRaggruppamento.intValue()) {
                    list.add(raggPdi);

                    break;
                }
            }
        }

        Collections.sort(list, new BeanComparator("ordinamento"));

        return list;
    }

    Boolean scaricamentoDatabaseRiuscitoConSuccesso() {
        return scaricamentoRiuscito;
    }

    //endregion
}
