#include <wx/wx.h>


using namespace std;

class AchordionApp : public wxApp {
public:
	virtual bool OnInit();
};
//THIS WILL BE THE FIRST WINDOW THAT THE USER WILL SEE
class WelcomeWindow : public wxFrame {
public:
	WelcomeWindow(const wxString& title);
private:
	void OnExit(wxCommandEvent& event);
	void OnAbout(wxCommandEvent& event);
	void OnRecordButtonClick(wxCommandEvent& event);
};

class RecordingWindow : public wxFrame {
public:
	RecordingWindow(const wxString& title);
private:
	void OnRecordingClick(wxCommandEvent& event);
};

bool AchordionApp::OnInit() {
	WelcomeWindow* frame = new WelcomeWindow("Achordion App");
	frame->Show(true);
	return true;

}

WelcomeWindow::WelcomeWindow(const wxString& title) : wxFrame(NULL, wxID_ANY, title, wxDefaultPosition, wxSize(700, 700)) {
	//creating menu bar
	wxMenu* FileMenu = new wxMenu;
	FileMenu->Append(wxID_EXIT, "&EXIT");
	wxMenu* HelpMenu = new wxMenu;
	HelpMenu->Append(wxID_ABOUT, "&ABOUT");
	//creating menus to the menu bar
	wxMenuBar* menuBar = new wxMenuBar;
	menuBar->Append(FileMenu, "&File");
	menuBar->Append(HelpMenu, "&Help");
	SetMenuBar(menuBar);

	//making a button that will display on the welcome screen
	wxPanel* panel = new wxPanel(this, wxID_ANY);
	//can adjust sizes here
	wxButton* record = new wxButton(panel, wxID_ANY, "CLICK TO RECORD", wxPoint(300, 300), wxSize(120, 70));

	//you have made a record button....  now you must bind it 
	record->Bind(wxEVT_BUTTON, &WelcomeWindow::OnRecordButtonClick, this);

	//must also bind all menu buttons
	Bind(wxEVT_MENU, &WelcomeWindow::OnExit, this, wxID_EXIT);
	Bind(wxEVT_MENU, &WelcomeWindow::OnAbout, this, wxID_ABOUT);
}

//it is bind... but what do you want to do with it? here is where you tell the app what to do

void WelcomeWindow::OnExit(wxCommandEvent& event) {
	Close(true);
}

void WelcomeWindow::OnAbout(wxCommandEvent& event) {
	//WHAT EXACTLY SHOULD THE ABOUT SAY????
	wxMessageBox("Achordion app for detecting chords??");
}

void WelcomeWindow::OnRecordButtonClick(wxCommandEvent& event) {
	Close(true);
	RecordingWindow* recording = new RecordingWindow("Ready to record!");
	recording->Show(true);
}

//when clicked... we enter the recording frame... here is where it is implemented

RecordingWindow::RecordingWindow(const wxString& title) : wxFrame(NULL, wxID_ANY, title, wxDefaultPosition, wxSize(700, 700)) {
	//make a button

	wxPanel* panel = new wxPanel(this, wxID_ANY);
	wxButton* startRecording = new wxButton(panel, wxID_ANY, "CLICK TO START", wxPoint(300, 300), wxSize(120, 100));
	//binding the button that was mentioned in the RecordingWindow
	startRecording->Bind(wxEVT_BUTTON, &RecordingWindow::OnRecordingClick, this);
}
//***************************************************************************
//         THIS IS WHERE THE RECORDING LOGIC SHOULD GO
//			INSTEAD OF WXMESSAGEBOX... WE SHOULD IMPLEMENT AUDIO INPUT LOGIC
//********************************************************
void RecordingWindow::OnRecordingClick(wxCommandEvent& event) {
	wxMessageBox("INSERT RECORDING LOGIC");
}






wxIMPLEMENT_APP(AchordionApp);  // This macro creates the main() function for the application