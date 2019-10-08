# CafRedirector
Allows opening *.caf (Core Audio Format) files in your mediaplayer of choice on Android.
Particularly useful for opening iMessage voice messages directly from [AirMessage](https://airmessage.org/)
in [VLC](https://www.videolan.org/vlc/download-android.html)
### Why?
Android doesn't recognize *.caf files as an audio format, as they are part of the Apple ecosystem.
Popular media players such as VLC are able to play these files, but the commonly used [Android API for 
retrieving the MIME type of files](https://developer.android.com/reference/android/webkit/MimeTypeMap.html#getMimeTypeFromExtension(java.lang.String))
doesn't know about that and returns 'unknown type' (`null`).
### Install
Download the latest apk from [releases](https://github.com/Schaback/CafRedirector/releases/latest)
### Uninstall
The App doesn't show up in the launcher, uninstall it from the "Apps"-Menu in Settings
### Building
Build like any other android project, nothing fancy here...
