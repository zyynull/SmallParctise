import pytesseract
from PIL import Image

image = Image.open('5EFFD14C-92A0-44B6-8DC9-E4E7E1458962.png')
content = pytesseract.image_to_string(image, lang='cn')  # 解析图片
print(content)
