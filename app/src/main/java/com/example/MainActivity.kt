package com.example

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Emergency
import androidx.compose.material.icons.filled.Healing
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocalHospital
import androidx.compose.material.icons.filled.MedicalServices
import androidx.compose.material.icons.filled.Medication
import androidx.compose.material.icons.filled.PersonalInjury
import androidx.compose.material.icons.filled.Translate
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.AppLanguage
import com.example.ui.localization.AppStrings
import com.example.ui.screens.EmergencySosDialog
import com.example.ui.screens.FacilitiesScreen
import com.example.ui.screens.FirstAidScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.MedicineScreen
import com.example.ui.screens.RecordsScreen
import com.example.ui.screens.TriageScreen
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.theme.TealOnPrimary
import com.example.ui.theme.TealPrimary
import com.example.ui.theme.UrgentRed
import com.example.ui.viewmodel.AppTab
import com.example.ui.viewmodel.JeevanViewModel
import com.example.ui.viewmodel.JeevanViewModelFactory

class MainActivity : ComponentActivity() {

    private val viewModel: JeevanViewModel by viewModels {
        JeevanViewModelFactory(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            MyApplicationTheme {
                MainAppScreen(
                    viewModel = viewModel,
                    onDialPhone = { number -> dialPhoneNumber(number) },
                    onOpenMap = { lat, lng, name -> openMapLocation(lat, lng, name) },
                    onSendSms = { message -> sendEmergencySms(message) }
                )
            }
        }
    }

    private fun dialPhoneNumber(phoneNumber: String) {
        try {
            val intent = Intent(Intent.ACTION_DIAL).apply {
                data = Uri.parse("tel:${phoneNumber.trim()}")
            }
            startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(this, "डायलर उघडू शकले नाही: $phoneNumber", Toast.LENGTH_SHORT).show()
        }
    }

    private fun openMapLocation(lat: Double, lng: Double, name: String) {
        try {
            val uri = Uri.parse("geo:$lat,$lng?q=${Uri.encode(name)}")
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(this, "नकाशा ॲप उपलब्ध नाही", Toast.LENGTH_SHORT).show()
        }
    }

    private fun sendEmergencySms(message: String) {
        try {
            val intent = Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse("sms:")
                putExtra("sms_body", message)
            }
            startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(this, "एसएमएस ॲप उपलब्ध नाही", Toast.LENGTH_SHORT).show()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainAppScreen(
    viewModel: JeevanViewModel,
    onDialPhone: (String) -> Unit,
    onOpenMap: (Double, Double, String) -> Unit,
    onSendSms: (String) -> Unit
) {
    val lang by viewModel.currentLanguage.collectAsState()
    val currentTab by viewModel.currentTab.collectAsState()
    val showEmergencyDialog by viewModel.showEmergencyDialog.collectAsState()

    // Triage states
    val triageStep by viewModel.triageStep.collectAsState()
    val selectedSymptoms by viewModel.selectedSymptoms.collectAsState()
    val customSymptomInput by viewModel.customSymptomInput.collectAsState()
    val selectedDuration by viewModel.selectedDuration.collectAsState()
    val selectedRedFlags by viewModel.selectedRedFlags.collectAsState()
    val isAnalyzing by viewModel.isAnalyzing.collectAsState()
    val triageResult by viewModel.triageResult.collectAsState()
    val isSpeaking by viewModel.isSpeaking.collectAsState()

    // Data streams
    val familyMembers by viewModel.familyMembers.collectAsState()
    val triageLogs by viewModel.triageLogs.collectAsState()
    val medicines by viewModel.medicines.collectAsState()
    val filteredFacilities by viewModel.filteredFacilities.collectAsState()
    val selectedFacilityType by viewModel.selectedFacilityType.collectAsState()
    val facilitySearch by viewModel.facilitySearch.collectAsState()

    val statusMessage by viewModel.statusMessage.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(statusMessage) {
        statusMessage?.let {
            snackbarHostState.showSnackbar(it)
            viewModel.clearStatusMessage()
        }
    }

    // Back handling for sub-steps and tabs
    BackHandler(enabled = currentTab != AppTab.HOME || triageStep > 1) {
        if (currentTab == AppTab.TRIAGE && triageStep > 1) {
            viewModel.previousTriageStep()
        } else {
            viewModel.setTab(AppTab.HOME)
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Surface(
                            shape = CircleShape,
                            color = TealPrimary,
                            modifier = Modifier.size(32.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.MedicalServices,
                                contentDescription = "Logo",
                                tint = Color.White,
                                modifier = Modifier
                                    .padding(6.dp)
                                    .fillMaxSize()
                            )
                        }
                        Column {
                            Text(
                                text = AppStrings.appName(lang),
                                fontWeight = FontWeight.ExtraBold,
                                fontSize = 20.sp,
                                color = TealPrimary
                            )
                        }
                    }
                },
                actions = {
                    // Quick language toggle chip
                    Surface(
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .clickable {
                                val nextLang = when (lang) {
                                    AppLanguage.MARATHI -> AppLanguage.HINDI
                                    AppLanguage.HINDI -> AppLanguage.ENGLISH
                                    AppLanguage.ENGLISH -> AppLanguage.MARATHI
                                }
                                viewModel.setLanguage(nextLang)
                            }
                            .testTag("topbar_lang_toggle"),
                        color = MaterialTheme.colorScheme.primaryContainer
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Translate,
                                contentDescription = "Switch Language",
                                tint = TealPrimary,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = lang.displayName,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = TealPrimary
                            )
                        }
                    }

                    // Emergency SOS Red Icon
                    IconButton(
                        onClick = { viewModel.openEmergencyDialog() },
                        modifier = Modifier.testTag("topbar_sos_button")
                    ) {
                        Surface(
                            shape = CircleShape,
                            color = UrgentRed,
                            modifier = Modifier.size(34.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Emergency,
                                contentDescription = "Emergency SOS",
                                tint = Color.White,
                                modifier = Modifier
                                    .padding(6.dp)
                                    .fillMaxSize()
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        },
        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.surface,
                tonalElevation = 8.dp,
                modifier = Modifier.testTag("bottom_navigation_bar")
            ) {
                val pendingMedsCount = medicines.count { !it.takenToday }

                NavigationBarItem(
                    selected = currentTab == AppTab.HOME,
                    onClick = { viewModel.setTab(AppTab.HOME) },
                    icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
                    label = { Text(AppStrings.tabHome(lang), fontSize = 11.sp, fontWeight = FontWeight.SemiBold) },
                    colors = NavigationBarItemDefaults.colors(selectedIconColor = TealPrimary, indicatorColor = MaterialTheme.colorScheme.primaryContainer),
                    modifier = Modifier.testTag("nav_item_home")
                )
                NavigationBarItem(
                    selected = currentTab == AppTab.TRIAGE,
                    onClick = { viewModel.setTab(AppTab.TRIAGE) },
                    icon = { Icon(Icons.Default.MedicalServices, contentDescription = "Triage") },
                    label = { Text(AppStrings.tabTriage(lang), fontSize = 11.sp, fontWeight = FontWeight.SemiBold) },
                    colors = NavigationBarItemDefaults.colors(selectedIconColor = TealPrimary, indicatorColor = MaterialTheme.colorScheme.primaryContainer),
                    modifier = Modifier.testTag("nav_item_triage")
                )
                NavigationBarItem(
                    selected = currentTab == AppTab.FIRST_AID,
                    onClick = { viewModel.setTab(AppTab.FIRST_AID) },
                    icon = { Icon(Icons.Default.Healing, contentDescription = "First Aid") },
                    label = { Text(AppStrings.tabFirstAid(lang), fontSize = 11.sp, fontWeight = FontWeight.SemiBold) },
                    colors = NavigationBarItemDefaults.colors(selectedIconColor = TealPrimary, indicatorColor = MaterialTheme.colorScheme.primaryContainer),
                    modifier = Modifier.testTag("nav_item_first_aid")
                )
                NavigationBarItem(
                    selected = currentTab == AppTab.FACILITIES,
                    onClick = { viewModel.setTab(AppTab.FACILITIES) },
                    icon = { Icon(Icons.Default.LocalHospital, contentDescription = "Facilities") },
                    label = { Text(AppStrings.tabFacilities(lang), fontSize = 11.sp, fontWeight = FontWeight.SemiBold) },
                    colors = NavigationBarItemDefaults.colors(selectedIconColor = TealPrimary, indicatorColor = MaterialTheme.colorScheme.primaryContainer),
                    modifier = Modifier.testTag("nav_item_facilities")
                )
                NavigationBarItem(
                    selected = currentTab == AppTab.MEDICINES,
                    onClick = { viewModel.setTab(AppTab.MEDICINES) },
                    icon = {
                        if (pendingMedsCount > 0) {
                            BadgedBox(badge = { Badge { Text("$pendingMedsCount") } }) {
                                Icon(Icons.Default.Medication, contentDescription = "Medicines")
                            }
                        } else {
                            Icon(Icons.Default.Medication, contentDescription = "Medicines")
                        }
                    },
                    label = { Text(AppStrings.tabMedicines(lang), fontSize = 11.sp, fontWeight = FontWeight.SemiBold) },
                    colors = NavigationBarItemDefaults.colors(selectedIconColor = TealPrimary, indicatorColor = MaterialTheme.colorScheme.primaryContainer),
                    modifier = Modifier.testTag("nav_item_medicines")
                )
                NavigationBarItem(
                    selected = currentTab == AppTab.RECORDS,
                    onClick = { viewModel.setTab(AppTab.RECORDS) },
                    icon = { Icon(Icons.Default.PersonalInjury, contentDescription = "Records") },
                    label = { Text(AppStrings.tabRecords(lang), fontSize = 11.sp, fontWeight = FontWeight.SemiBold) },
                    colors = NavigationBarItemDefaults.colors(selectedIconColor = TealPrimary, indicatorColor = MaterialTheme.colorScheme.primaryContainer),
                    modifier = Modifier.testTag("nav_item_records")
                )
            }
        },
        floatingActionButton = {
            if (currentTab != AppTab.TRIAGE) {
                FloatingActionButton(
                    onClick = { viewModel.startVoiceTriageFlow() },
                    containerColor = TealPrimary,
                    contentColor = TealOnPrimary,
                    modifier = Modifier.testTag("fab_voice_triage")
                ) {
                    Icon(
                        imageVector = Icons.Default.MedicalServices,
                        contentDescription = "Start Triage"
                    )
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (currentTab) {
                AppTab.HOME -> HomeScreen(
                    lang = lang,
                    onSelectLanguage = { viewModel.setLanguage(it) },
                    onNavigateTab = { viewModel.setTab(it) },
                    onOpenEmergency = { viewModel.openEmergencyDialog() },
                    onCallPhone = onDialPhone,
                    medicines = medicines
                )

                AppTab.TRIAGE -> TriageScreen(
                    lang = lang,
                    triageStep = triageStep,
                    selectedSymptoms = selectedSymptoms,
                    customSymptomInput = customSymptomInput,
                    selectedDuration = selectedDuration,
                    selectedRedFlags = selectedRedFlags,
                    isAnalyzing = isAnalyzing,
                    triageResult = triageResult,
                    isSpeaking = isSpeaking,
                    onToggleSymptom = { viewModel.toggleSymptom(it) },
                    onSetCustomSymptom = { viewModel.setCustomSymptomInput(it) },
                    onAppendVoiceTranscript = { viewModel.appendVoiceTranscript(it) },
                    onSetDuration = { viewModel.setDuration(it) },
                    onToggleRedFlag = { viewModel.toggleRedFlag(it) },
                    onNextStep = { viewModel.nextTriageStep() },
                    onPreviousStep = { viewModel.previousTriageStep() },
                    onRestartTriage = { viewModel.resetTriage() },
                    onSaveToRecord = { viewModel.saveCurrentTriageToRecord() },
                    onSpeak = { viewModel.speak(it) },
                    onStopAudio = { viewModel.stopAudio() },
                    onNavigateFacilities = { viewModel.setTab(AppTab.FACILITIES) },
                    onCallAmbulance = { onDialPhone("108") }
                )

                AppTab.FIRST_AID -> FirstAidScreen(
                    lang = lang,
                    isSpeaking = isSpeaking,
                    onSpeak = { viewModel.speak(it) },
                    onStopAudio = { viewModel.stopAudio() },
                    onCallEmergency = onDialPhone
                )

                AppTab.FACILITIES -> FacilitiesScreen(
                    lang = lang,
                    facilities = filteredFacilities,
                    selectedType = selectedFacilityType,
                    searchQuery = facilitySearch,
                    onSelectType = { viewModel.setFacilityType(it) },
                    onSearchChange = { viewModel.setFacilitySearch(it) },
                    onCallPhone = onDialPhone,
                    onOpenMap = { item -> onOpenMap(item.latitude, item.longitude, item.getName(lang)) },
                    onAddCustomFacility = { name, phone, address ->
                        viewModel.addCustomFacility(name, phone, address)
                    }
                )

                AppTab.MEDICINES -> MedicineScreen(
                    lang = lang,
                    medicines = medicines,
                    onToggleTaken = { viewModel.toggleMedicineTaken(it) },
                    onAddMedicine = { name, dosage, timing, morn, noon, night ->
                        viewModel.addMedicine(name, dosage, timing, morn, noon, night)
                    },
                    onDeleteMedicine = { viewModel.deleteMedicine(it) }
                )

                AppTab.RECORDS -> RecordsScreen(
                    lang = lang,
                    familyMembers = familyMembers,
                    triageLogs = triageLogs,
                    onAddFamilyMember = { name, rel, age, gen, bld, abha, chronic, phone ->
                        viewModel.addFamilyMember(name, rel, age, gen, bld, abha, chronic, phone)
                    },
                    onDeleteFamilyMember = { viewModel.deleteFamilyMember(it) },
                    onDeleteTriageLog = { viewModel.deleteTriageLog(it) },
                    onCallPhone = onDialPhone
                )
            }

            // Emergency SOS Modal Dialog
            if (showEmergencyDialog) {
                EmergencySosDialog(
                    lang = lang,
                    onDismiss = { viewModel.closeEmergencyDialog() },
                    onCall = onDialPhone,
                    onSendSms = onSendSms
                )
            }
        }
    }
}
