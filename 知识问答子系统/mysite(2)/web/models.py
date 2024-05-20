from django.db import models

# Create your models here.
from django.db import models

# Create your models here.

from django.db import models

class Artifact1(models.Model):
    id = models.AutoField(primary_key=True)
    artifactName = models.CharField(max_length=255, null=False)
    artifactName_Chinese= models.CharField(max_length=255, null=False)
    country = models.CharField(max_length=255, blank=True, null=True)
    relicTime = models.CharField(max_length=255, blank=True, null=True)
    relicTime_Chinese= models.CharField(max_length=255, blank=True, null=True)
    material = models.CharField(max_length=255, blank=True, null=True)
    material_Chinese = models.CharField(max_length=255, blank=True, null=True)
    library = models.CharField(max_length=255, blank=True, null=True)
    library_Chinese= models.CharField(max_length=255, blank=True, null=True)
    size = models.CharField(max_length=255, blank=True, null=True)
    size_Chinese = models.CharField(max_length=255, blank=True, null=True)
    description = models.CharField(max_length=255, blank=True, null=True)
    description_Chinese = models.CharField(max_length=255, blank=True, null=True)
    moreUrl = models.CharField(max_length=255, blank=True, null=True)
    imageUrl = models.CharField(max_length=255, blank=True, null=True)

    class Meta:
        managed=False
        db_table='artifact'
