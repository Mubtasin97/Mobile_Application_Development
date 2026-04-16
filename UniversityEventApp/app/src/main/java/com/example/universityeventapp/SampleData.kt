package com.example.universityeventapp

object SampleData {
    fun getEvents(): List<Event> = listOf(
        Event(1, "Tech Summit 2025", "2026-05-01", "10:00 AM", "Auditorium A", "Tech",
            "Annual technology summit featuring top industry speakers.", 150.0, 200, 120,
            R.drawable.event_tech,
            listOf("Dr. Alice Rahman - AI Expert")
        ),
        Event(2, "Sport Tournament", "2026-05-02", "9:00 AM", "Sports Ground", "Sports",
            "Inter-department football championship.", 50.0, 300, 200,
            R.drawable.event_sports,
            listOf("Coach Karim Ahmed - Head Coach")
        ),
        Event(3, "Cultural Night", "2026-05-03", "6:00 PM", "Main Hall", "Cultural",
            "Evening of music, dance, and art.", 100.0, 250, 180,
            R.drawable.event_cultural,
            listOf("Minar Rahman - Lead Singer")
        ),
        Event(4, "Academic Conference", "2026-05-04", "9:00 AM", "Seminar Room 1", "Academic",
            "Research papers presentation.", 50.0, 100, 60,
            R.drawable.event_academic,
            listOf("Prof. Dr. Rahman Khan - Dean")
        ),
        Event(5, "Social Mixer", "2026-05-04", "5:00 PM", "Cafeteria", "Social",
            "End-of-semester social gathering.", 30.0, 150, 100,
            R.drawable.event_social,
            listOf("Student Council President")
        ),
        Event(6, "AI Workshop", "2026-05-05", "11:00 AM", "CS Lab", "Tech",
            "Hands-on workshop on machine learning.", 200.0, 50, 30,
            R.drawable.event_tech,
            listOf("Engr. Tanvir Ahmed - ML Engineer")
        ),
        Event(7, "Basketball Cup", "2026-05-06", "3:00 PM", "Indoor Court", "Sports",
            "University basketball championship finals.", 50.0, 200, 150,
            R.drawable.event_sports,
            listOf("Coach Mahmud Hassan")
        ),
        Event(8, "Art Exhibition", "2026-05-07", "10:00 AM", "Gallery Hall", "Cultural",
            "Annual fine arts exhibition.", 20.0, 120, 80,
            R.drawable.event_cultural,
            listOf("Prof. Arif Mahmud - Art Professor")
        )
    )
}