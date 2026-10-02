package com.example.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Emergency
import androidx.compose.material.icons.filled.Message
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.example.ui.theme.UrgentRed

@Composable
fun EmergencySosDialog(
    lang: AppLanguage,
    onDismiss: () -> Unit,
    onCall: (String) -> Unit,
    onSendSms: (String) -> Unit,
    emergencyFamilyNumber: String = ""
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        modifier = Modifier.testTag("emergency_sos_dialog"),
        title = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Surface(
                        shape = CircleShape,
                        color = Color(0xFFFFEBEE),
                        modifier = Modifier.size(36.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Emergency,
                            contentDescription = "SOS",
                            tint = UrgentRed,
                            modifier = Modifier
                                .padding(6.dp)
                                .size(24.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = AppStrings.sosButton(lang),
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 18.sp,
                        color = UrgentRed
                    )
                }
                IconButton(onClick = onDismiss) {
                    Icon(Icons.Default.Close, contentDescription = "Close")
                }
            }
        },
        text = {
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // 108 Ambulance Button (Gigantic Red Card)
                item {
                    EmergencyDialCard(
                        title = AppStrings.callAmbulance108(lang),
                        subtitle = "मोफत आपत्कालीन रुग्णवाहिका (Free Emergency Ambulance)",
                        number = "108",
                        onCall = onCall,
                        bgColor = UrgentRed,
                        textColor = Color.White
                    )
                }

                // 102 Pregnant Women & Infant
                item {
                    EmergencyDialCard(
                        title = AppStrings.callMaternity102(lang),
                        subtitle = "गरोदर माता व नवजात बाळ वाहतूक (Maternity Transport)",
                        number = "102",
                        onCall = onCall,
                        bgColor = Color(0xFF0284C7),
                        textColor = Color.White
                    )
                }

                // 104 Health Helpline
                item {
                    EmergencyDialCard(
                        title = AppStrings.callHealthHelpline104(lang),
                        subtitle = "आरोग्य सल्ला व मोफत मार्गदर्शन (Tele-Health Advice)",
                        number = "104",
                        onCall = onCall,
                        bgColor = Color(0xFF0D9488),
                        textColor = Color.White
                    )
                }

                // 112 National Police / Disaster Emergency
                item {
                    EmergencyDialCard(
                        title = AppStrings.callPoliceEmergency112(lang),
                        subtitle = "पोलीस व आपत्ती व्यवस्थापन (All Emergencies)",
                        number = "112",
                        onCall = onCall,
                        bgColor = Color(0xFF475569),
                        textColor = Color.White
                    )
                }

                // Send SOS SMS to Family
                item {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("send_sos_sms_card")
                            .clip(RoundedCornerShape(12.dp))
                            .clickable {
                                onSendSms(
                                    "तातडीची मदत हवी! मला वैद्यकीय समस्येत तातडीची गरज आहे. कृपया त्वरित मला संपर्क करा! (Medical Emergency! I need urgent help. Please contact me immediately!)"
                                )
                            },
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF7ED)),
                        border = BorderStroke(1.dp, Color(0xFFFDBA74))
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(Icons.Default.Message, contentDescription = "SMS", tint = Color(0xFFEA580C))
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Text(
                                    text = AppStrings.sendEmergencySms(lang),
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp,
                                    color = Color(0xFF9A3412)
                                )
                                Text(
                                    text = "स्थान व आपत्कालीन संदेश कुटुंबाला पाठवा",
                                    fontSize = 12.sp,
                                    color = Color(0xFFC2410C)
                                )
                            }
                        }
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = onDismiss,
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Text(text = "बंद करा", color = MaterialTheme.colorScheme.onSurface)
            }
        }
    )
}

@Composable
private fun EmergencyDialCard(
    title: String,
    subtitle: String,
    number: String,
    onCall: (String) -> Unit,
    bgColor: Color,
    textColor: Color
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("emergency_dial_$number")
            .clip(RoundedCornerShape(14.dp))
            .clickable { onCall(number) },
        colors = CardDefaults.cardColors(containerColor = bgColor)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 16.sp,
                    color = textColor
                )
                Text(
                    text = subtitle,
                    fontSize = 12.sp,
                    color = textColor.copy(alpha = 0.85f)
                )
            }
            Surface(
                shape = CircleShape,
                color = Color.White,
                modifier = Modifier.size(40.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Phone,
                    contentDescription = "Call $number",
                    tint = bgColor,
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxSize()
                )
            }
        }
    }
}
