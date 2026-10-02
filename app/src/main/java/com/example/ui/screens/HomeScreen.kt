package com.example.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Emergency
import androidx.compose.material.icons.filled.LocalHospital
import androidx.compose.material.icons.filled.MedicalServices
import androidx.compose.material.icons.filled.Medication
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.PersonalInjury
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.local.entities.MedicineReminderEntity
import com.example.data.model.AppLanguage
import com.example.ui.components.LanguageSelectorBar
import com.example.ui.components.MedicalDisclaimerCard
import com.example.ui.localization.AppStrings
import com.example.ui.theme.HealthGreen
import com.example.ui.theme.SoftBlue
import com.example.ui.theme.TealPrimary
import com.example.ui.theme.UrgentRed
import com.example.ui.viewmodel.AppTab

@Composable
fun HomeScreen(
    lang: AppLanguage,
    onSelectLanguage: (AppLanguage) -> Unit,
    onNavigateTab: (AppTab) -> Unit,
    onOpenEmergency: () -> Unit,
    onCallPhone: (String) -> Unit,
    medicines: List<MedicineReminderEntity>,
    modifier: Modifier = Modifier
) {
    val pendingMeds = medicines.count { !it.takenToday }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .testTag("home_screen"),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Language Selector Bar
        item {
            LanguageSelectorBar(
                selectedLanguage = lang,
                onSelectLanguage = onSelectLanguage
            )
        }

        // Hero Card with Illustration & Quick Greeting
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("hero_banner_card"),
                shape = RoundedCornerShape(20.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(160.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.img_hero_health),
                            contentDescription = "Rural healthcare illustration",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(
                                    Brush.verticalGradient(
                                        colors = listOf(Color.Transparent, Color(0xCC003630))
                                    )
                                )
                        )
                        Text(
                            text = AppStrings.appName(lang),
                            color = Color.White,
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 24.sp,
                            modifier = Modifier
                                .align(Alignment.BottomStart)
                                .padding(16.dp)
                        )
                    }
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = AppStrings.welcomeHeading(lang),
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = AppStrings.appTagline(lang),
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }
                }
            }
        }

        // BIG RED SOS EMERGENCY BANNER (One-Touch Emergency Assistance)
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("emergency_sos_banner")
                    .clip(RoundedCornerShape(16.dp))
                    .clickable { onOpenEmergency() },
                colors = CardDefaults.cardColors(containerColor = UrgentRed),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Surface(
                            shape = CircleShape,
                            color = Color.White,
                            modifier = Modifier.size(46.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Emergency,
                                contentDescription = "SOS",
                                tint = UrgentRed,
                                modifier = Modifier
                                    .padding(8.dp)
                                    .fillMaxSize()
                            )
                        }
                        Column {
                            Text(
                                text = AppStrings.sosButton(lang),
                                color = Color.White,
                                fontWeight = FontWeight.ExtraBold,
                                fontSize = 18.sp
                            )
                            Text(
                                text = "१०८ रुग्णवाहिका / १०२ माता / आपत्कालीन मदत",
                                color = Color(0xFFFFCDD2),
                                fontSize = 13.sp
                            )
                        }
                    }
                    Button(
                        onClick = { onOpenEmergency() },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.White,
                            contentColor = UrgentRed
                        ),
                        shape = RoundedCornerShape(12.dp),
                        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
                    ) {
                        Text(text = "SOS", fontWeight = FontWeight.Black, fontSize = 16.sp)
                    }
                }
            }
        }

        // PRIMARY ACTION TILES (Large, high-contrast, accessible)
        item {
            Text(
                text = "आरोग्य सेवा व तपासणी",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(top = 4.dp)
            )
        }

        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                HomeFeatureTile(
                    title = AppStrings.startVoiceTriage(lang),
                    subtitle = "माईकवर बोलून तपासणी करा",
                    icon = Icons.Default.Mic,
                    iconTint = Color.White,
                    containerColor = TealPrimary,
                    textColor = Color.White,
                    modifier = Modifier.weight(1f),
                    testTag = "voice_triage_tile",
                    onClick = { onNavigateTab(AppTab.TRIAGE) }
                )
                HomeFeatureTile(
                    title = AppStrings.tabTriage(lang),
                    subtitle = "लक्षणे निवडून AI सल्ला",
                    icon = Icons.Default.MedicalServices,
                    iconTint = TealPrimary,
                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                    textColor = MaterialTheme.colorScheme.onSecondaryContainer,
                    modifier = Modifier.weight(1f),
                    testTag = "symptom_triage_tile",
                    onClick = { onNavigateTab(AppTab.TRIAGE) }
                )
            }
        }

        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                HomeFeatureTile(
                    title = AppStrings.tabFirstAid(lang),
                    subtitle = "सर्पदंश, उष्माघात, भाजणे",
                    icon = Icons.Default.Warning,
                    iconTint = Color(0xFFC2410C),
                    containerColor = Color(0xFFFFEDD5),
                    textColor = Color(0xFF7C2D12),
                    modifier = Modifier.weight(1f),
                    testTag = "first_aid_tile",
                    onClick = { onNavigateTab(AppTab.FIRST_AID) }
                )
                HomeFeatureTile(
                    title = AppStrings.tabFacilities(lang),
                    subtitle = "PHC, CHC, जन औषधी",
                    icon = Icons.Default.LocalHospital,
                    iconTint = SoftBlue,
                    containerColor = Color(0xFFE0F2FE),
                    textColor = Color(0xFF0369A1),
                    modifier = Modifier.weight(1f),
                    testTag = "facilities_tile",
                    onClick = { onNavigateTab(AppTab.FACILITIES) }
                )
            }
        }

        // MEDICINE & HEALTH RECORD STRIP
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                HomeFeatureTile(
                    title = AppStrings.tabMedicines(lang),
                    subtitle = if (pendingMeds > 0) "$pendingMeds बाकी औषधे" else "सर्व घेतली ✓",
                    icon = Icons.Default.Medication,
                    iconTint = HealthGreen,
                    containerColor = Color(0xFFDCFCE7),
                    textColor = Color(0xFF14532D),
                    modifier = Modifier.weight(1f),
                    testTag = "medicines_tile",
                    onClick = { onNavigateTab(AppTab.MEDICINES) }
                )
                HomeFeatureTile(
                    title = AppStrings.tabRecords(lang),
                    subtitle = "कुटुंब व तपासणी इतिहास",
                    icon = Icons.Default.PersonalInjury,
                    iconTint = Color(0xFF6B21A8),
                    containerColor = Color(0xFFF3E8FF),
                    textColor = Color(0xFF581C87),
                    modifier = Modifier.weight(1f),
                    testTag = "records_tile",
                    onClick = { onNavigateTab(AppTab.RECORDS) }
                )
            }
        }

        // QUICK TOLL-FREE CALL BUTTONS
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("toll_free_card"),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "मोफत आपत्कालीन संपर्क (Toll-Free Helplines)",
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        TollFreeChip(
                            label = "१०८ रुग्णवाहिका",
                            number = "108",
                            onCall = onCallPhone,
                            modifier = Modifier.weight(1f)
                        )
                        TollFreeChip(
                            label = "१०२ माता वाहन",
                            number = "102",
                            onCall = onCallPhone,
                            modifier = Modifier.weight(1f)
                        )
                        TollFreeChip(
                            label = "१०४ आरोग्य सल्ला",
                            number = "104",
                            onCall = onCallPhone,
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }
        }

        // Medical Disclaimer
        item {
            MedicalDisclaimerCard(lang = lang)
        }
    }
}

@Composable
private fun HomeFeatureTile(
    title: String,
    subtitle: String,
    icon: ImageVector,
    iconTint: Color,
    containerColor: Color,
    textColor: Color,
    onClick: () -> Unit,
    testTag: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .testTag(testTag)
            .height(118.dp)
            .clip(RoundedCornerShape(16.dp))
            .clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = containerColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(14.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Surface(
                shape = CircleShape,
                color = iconTint.copy(alpha = 0.2f),
                modifier = Modifier.size(38.dp)
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = title,
                    tint = iconTint,
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxSize()
                )
            }
            Column {
                Text(
                    text = title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                    color = textColor,
                    maxLines = 1
                )
                Text(
                    text = subtitle,
                    fontSize = 12.sp,
                    color = textColor.copy(alpha = 0.8f),
                    maxLines = 1
                )
            }
        }
    }
}

@Composable
private fun TollFreeChip(
    label: String,
    number: String,
    onCall: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .clickable { onCall(number) },
        color = MaterialTheme.colorScheme.surface,
        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFCBD5E1))
    ) {
        Row(
            modifier = Modifier.padding(vertical = 10.dp, horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = Icons.Default.Phone,
                contentDescription = "Call $number",
                tint = TealPrimary,
                modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = label,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}
