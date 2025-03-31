package com.tanh.tourbooking.util

import com.google.auth.oauth2.GoogleCredentials
import java.io.ByteArrayInputStream
import java.io.IOException
import java.nio.charset.StandardCharsets

object AccessToken {

    private const val FIREBASE_MESSAGING_SCOPE = "https://www.googleapis.com/auth/firebase.messaging"

    fun getAccessToken(): String? {
        try {

            val jsonString: String = """
                {
                  "type": "service_account",
                  "project_id": "noteapp-d7b38",
                  "private_key_id": "4dc0e180dae9440a65dd0d87b19119026ebde155",
                  "private_key": "-----BEGIN PRIVATE KEY-----\nMIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCwgvGEaQKctdx0\nhoesyxYqXT0f9YtTItSiDkugfHHpdyFsqLsvFvjhV3JQely+5OVOim9psmYO/KlL\nglHYguJEwo8FSJD6+1+qHxdpgjjmccTAzxL7S2LJOThGgrCT/sL/1jR7+7d3eTf0\n5Pe4/PPV3KmXkuVUGZZgHf26MPFtZDqmsbCxwwyJWR2uKUtaLVgvD/+p7iAmdWVA\n+UdN9A06L76LccGYOQJp3fk2zvjUlBZjdkhTmCCaaxh2aT4pl/j/LtGOiFxLURuI\ng5YNloNzje6KKyFbSQ8BPSvhOctAuMvA+i+df04/L3HLcZCqYhHcev2z65moCXym\nW6j+nyiBAgMBAAECggEABjZYltn9ipuLtpHuy+ACr+Nj0ahyIq8b09eMFboVhgOQ\naocqfdz5jdBGpKKW2azbjXLpuosJ5KKDuJWtyOCL40LD9voTqLKwzm9tUvQ/aYxk\n4eqT0uJ3h8ie1Hv4aCAH98o/owK0JYU/mPvXRGRqNktfsGcOEVJ8eHXrwnojIB//\nGyY6CcXx4B/nVUVjvXzx4fLcwjthLiBjMj4EQfgdG2r3bFuoo6H6Fy3kx+dxMZeh\n00IckeayH4ZXeBcZPkrYDx/IdMCSRGi/E4qlaCKV8Ur309cXy7vFCtqWMy4ou1A6\nSHE94yBQkW7nLp0VH3ecAbcQ5VeXCE4TzHQjLEp5cQKBgQD4lJvH4ubKrWyNzoLp\nzoPLbST3Iz8Zc204tNhChdn2UHGNsdAlxa2tcTGT/URmVObGl/aZmDE1IpdeYApO\nse5/S7gNZ5VzXDlwMcnEIHw4KYMq82t4PGdcKJTm5x+MJ7llfu1veBl9I3hK7egR\n9YwjPLjIqiKpntxEt1Pg9FD5UQKBgQC1x6i/ABljVcAoYtAI6+41xpnX9ylJ8X1a\n5CHoFgI7iRXXFKBBnppfq9nUFtb/SmM3PLhamFQDtxgMJIAY+2NDsfggmBHr/sZf\nV7pRea0mYOUFlBR1/tHaLX/+6gLWfOXjq0jAWt8R8jPKw7sYFDJlCiVnan+U7v3+\n4CwNqkxwMQKBgQCrhL7IeUON7YPNhQTHlvtRTgoHjIZpZT+jtQTTQdBXKMJxZpOc\nvH1OA6CV4Fdl1Ic32HsLF/w8EZYoRFRYYGWT6AcjGKoCY0wuoev6LAAKMHqVFEi/\nOc6HxGiZujgGWRKPebkTBOdxdRv3d5h9a9Q6LBfEPcCVEUwjDewOeAW3QQKBgF2H\ndVORAX8Sx+P/s3pjqhURMmbkTOI21oDFkIyRhmSLjCk1+57HtDY1fcaaWdTQuWva\nWiCC7/dZZY4Z2a8A6XoTP8/FbrM2S2sTAFKbgHOWtz+/iSeNDPxtHMZUMVGawc36\ne1w9ZTkenXdebg1dlvBV4BjtuucucJGLVh9R2DKBAoGBAJ6bAxlQGnx1ZlA3ofXs\nfv66s27kY4wTgLcVHBvf+MFkjGFqTai/a+tlMbEdxznT9LaHzN4MhBhc96RFqdFL\n6inDAu/98ZitdqYlwZpyF1Om/hYyi3f7wtsdTxOa8iJCbzFMz7y4qDK3KMmQMKfz\n11JJGH1xp4Xc3SVpid5e1Y1K\n-----END PRIVATE KEY-----\n",
                  "client_email": "firebase-adminsdk-5plx6@noteapp-d7b38.iam.gserviceaccount.com",
                  "client_id": "110368743397928052591",
                  "auth_uri": "https://accounts.google.com/o/oauth2/auth",
                  "token_uri": "https://oauth2.googleapis.com/token",
                  "auth_provider_x509_cert_url": "https://www.googleapis.com/oauth2/v1/certs",
                  "client_x509_cert_url": "https://www.googleapis.com/robot/v1/metadata/x509/firebase-adminsdk-5plx6%40noteapp-d7b38.iam.gserviceaccount.com",
                  "universe_domain": "googleapis.com"
                }
            """.trimIndent()
            val stream = ByteArrayInputStream(jsonString.toByteArray(StandardCharsets.UTF_8))

            val googleCredentials = GoogleCredentials.fromStream(stream)
                .createScoped(listOf(FIREBASE_MESSAGING_SCOPE))

            googleCredentials.refresh()
            return googleCredentials.accessToken.tokenValue
        } catch (e: IOException) {
            return null
        }
    }

}