from PIL import Image
import pytesseract
# Xnip2020-02-14_13-58-15的副本.jpg
image = Image.open('Xnip2019-11-25_19-38-53.jpg')
content = pytesseract.image_to_string(image)   # 解析图片
print(content)
