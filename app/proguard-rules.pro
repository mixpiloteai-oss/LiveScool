# Routes de navigation type-safe (kotlinx.serialization)
-keepclassmembers @kotlinx.serialization.Serializable class com.livemachine.academy.** {
    *** Companion;
    kotlinx.serialization.KSerializer serializer(...);
}
-keep,includedescriptorclasses class com.livemachine.academy.**$$serializer { *; }
