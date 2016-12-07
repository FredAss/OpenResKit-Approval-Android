package htw.plantar.activities;

import htw.plantar.functions.DocumentAdapter;
import htw.plantar.functions.FileManager;
import htw.plantar.models.Document;
import htw.plantar.models.Plant;
import java.io.File;
import java.util.List;
import com.example.plantar.R;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import roboguice.fragment.RoboFragment;

public class PlantDocumentFragment extends RoboFragment
{
	ListView m_DocumentListView;
	
	private ViewGroup m_View;
	private List<Document> m_Documents;
	private DocumentAdapter m_DocumentAdapter;
	private String m_SelectedDocument;
	private Plant m_SelectedPlant;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) 
	{
		m_View = (ViewGroup) inflater.inflate(R.layout.plant_document_fragment_layout, container, false);	
		InitializeComponents();
		
		if(m_SelectedPlant != null)
			ChangeData(m_SelectedPlant, false);

		return m_View;
	}
	
	private void InitializeComponents()
	{
		m_DocumentListView = (ListView)m_View.findViewById(R.id.documentList);
		m_DocumentListView.setOnItemClickListener(ReadDocument);
	}
	
	@Override
	public void onResume() 
	{
		super.onResume();
		if(m_SelectedDocument != null)
		{
			FileManager fm = new FileManager(getActivity());
			fm.DeleteDocument(m_SelectedDocument);
			m_SelectedDocument = null;
		}
	}
	
	@Override
	public void onDestroyView() 
	{
		super.onDestroyView();
		if(m_SelectedDocument != null)
		{
			FileManager fm = new FileManager(getActivity());
			fm.DeleteDocument(m_SelectedDocument);
			m_SelectedDocument = null;
		}
	}
	
	private OnItemClickListener ReadDocument = new OnItemClickListener() 
	{
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) 
		{
			if(m_SelectedPlant.getAttachedDocuments().get(position).getName().endsWith(".pdf"))
				OpenPDFDocument(position);
			else
				OpenImageDocument(position);
		}
	};
	
	private void OpenPDFDocument(int position)
	{
		m_SelectedDocument = m_SelectedPlant.getAttachedDocuments().get(position).getName();

		FileManager fm = new FileManager(getActivity());
		fm.SaveDocumentToExternal(m_SelectedPlant.getAttachedDocuments().get(position).getDocumentSource().getBinarySource(), m_SelectedPlant.getAttachedDocuments().get(position).getName());
		
		File fileToRead = new File(fm.getAppRootDir().getPath() + "/" + m_SelectedPlant.getAttachedDocuments().get(position).getName());
					
		Intent intent = new Intent(Intent.ACTION_VIEW);		
		intent.setDataAndType(Uri.fromFile(fileToRead), "application/pdf");
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		
		try
		{
			startActivity(intent);
		}
		catch(ActivityNotFoundException e)
		{
			Toast.makeText(getActivity(), "No Application found", Toast.LENGTH_SHORT).show();;
		}
	}
	
	private void OpenImageDocument(int position)
	{
		Document selectedDocument = m_SelectedPlant.getAttachedDocuments().get(position);
		
		LayoutInflater inflater = getActivity().getLayoutInflater();
		View dialogLayout = inflater.inflate(R.layout.dialog_document_image_layout, null);
		AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(getActivity(), R.style.AlertDialogCustom));
		
		builder.setTitle(selectedDocument.getName());
		
		ImageView imageView = (ImageView)dialogLayout.findViewById(R.id.imageDocuemnt);
		
		Bitmap bm = BitmapFactory.decodeByteArray(selectedDocument.getDocumentSource().getBinarySource(), 0, selectedDocument.getDocumentSource().getBinarySource().length);
		DisplayMetrics dm = new DisplayMetrics();
		getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
		
		imageView.setMinimumHeight(dm.heightPixels);
		imageView.setMinimumWidth(dm.widthPixels);
		imageView.setImageBitmap(bm);
		
		builder.setNegativeButton(getResources().getString(R.string.cancel_string), new DialogInterface.OnClickListener() 
		{
			@Override
			public void onClick(DialogInterface dialog, int which) 
			{
				dialog.dismiss();
			}
		});
		
		builder.setView(dialogLayout);
		builder.show();
	}
		
	public void ChangeData(Plant plant, boolean fromCamera)
	{
		m_SelectedPlant = plant;
		
		if(!fromCamera)
		{
			m_Documents = plant.getAttachedDocuments();
			m_DocumentListView.setAdapter(null);
			m_DocumentAdapter = null;
			
			m_DocumentAdapter = new DocumentAdapter(getActivity(), R.layout.document_item_row, m_Documents);
			m_DocumentListView.setAdapter(m_DocumentAdapter);
		}
	}
}
