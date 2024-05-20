from django.shortcuts import render

# Create your views here.
from django.shortcuts import render


# Create your views here.
from django.shortcuts import render
from app01 import models
artifact_info = { "序号": "id", "文物名称": "artifactName", "国籍": "country", "朝代": "relicTime", "材质": "material", "尺码": "size", "描述": "description", "更多网址": "moreUrl", "照片": "imageUrl" }
# Existing view function
def student_list(request):
    student_queryset = models.Artifact1.objects.all()
    return render(request, "student.html", {"student_queryset": student_queryset})

# New function to get address by name

def get_value_by_name_and_col(request, name, col):
    try:
        student = models.Artifact1.objects.get(artifactName=name)
        value = getattr(student, col)
        print(value)
        return value
    except models.Artifact1.DoesNotExist:
        return  f"Value for column '{col}' not found for this name"
    except AttributeError:
        return  f"Column '{col}' not found for this name"


def chat_view(request):
    return render(request, 'gpt.html')
from django.shortcuts import render
import requests
import json

def home(request):
    return render(request, 'gpt.html')
from django.http import JsonResponse
import requests
from django.http import JsonResponse
import requests

def extract_str1_str2(sentence):
    # 检查句子是否符合格式
    if '是什么' in sentence and '的' in sentence:
        # 提取str1和str2
        parts = sentence.split('是什么')
        if len(parts) == 2:
            str1_str2 = parts[0].strip().strip('的')
            str1, str2 = str1_str2.rsplit('的', 1)
            return True, str1, str2
    return False, None, None



def chatbot_api(request):
    if request.method == 'POST':
        data = json.loads(request.body)
        message = data.get('message', '')

        print("用户输入："+message)

        result, str1, str2 = extract_str1_str2(message)
        for key, value in artifact_info.items():
            if str2 == key:
                str2 = value
        if message and result==False:
            response = call_chatgpt_api(message)
            print("response: "+response)

            return JsonResponse({'response': response})

        elif message and result==True :
            print(f"格式匹配，str1: {str1}, str2: {str2}")
            response=get_value_by_name_and_col(request,str1,str2)
            return JsonResponse({'response': response})

    return JsonResponse({'response': 'Error'}, status=400)

def call_chatgpt_api(message):
    url = 'https://api.xty.app/v1/chat/completions'
    headers = {
        'Authorization': 'Bearer sk-aC69ocMI5Ju7deA1DaCfAb146a594cC988F54f356fEf6b82',
        'Content-Type': 'application/json'
    }
    data = {
        "model": "gpt-3.5-turbo",
        "messages": [{
            "role": "user",
            "content": message
        }]
    }
    response = requests.post(url, headers=headers, json=data)
    if response.status_code == 200:
        reply = response.json()['choices'][0]['message']['content']
        return reply
    else:
        return 'Error'
