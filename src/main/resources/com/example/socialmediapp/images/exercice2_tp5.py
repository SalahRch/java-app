import requests
from bs4 import BeautifulSoup
from googletrans import Translator
import csv


# Fonction pour extraire le contenu d'une page Wikipedia
def obtenir_contenu_wikipedia(url):
    response = requests.get(url)
    soup = BeautifulSoup(response.text, 'html.parser')
    paragraphs = soup.find_all('p')
    content = '\n'.join([p.get_text() for p in paragraphs])
    return content


# Fonction pour traduire un texte de français à arabe
# Fonction pour traduire un texte de français à arabe
def traduire_en_arabe(texte):
    traducteur = Translator()

    try:
        # Limiter la longueur du texte pour la traduction
        texte_limite = texte[:5000]  # Limitez à une longueur arbitraire, ajustez si nécessaire
        traduction = traducteur.translate(texte_limite, dest='ar')

        if traduction and traduction.text:
            return traduction.text
        else:
            print(f"La traduction pour le texte suivant est vide : {texte}")
            return None
    except Exception as e:
        print(f"Erreur lors de la traduction : {e}")
        return None

# Liste des termes en français
termes_fr = ['ordinateur', 'algorithme', 'cryptographie']

# Stockage des données extraites
donnees = []

# Parcours des termes en français
for terme_fr in termes_fr:
    # Création du lien de la page Wikipedia
    url_fr = f'https://fr.wikipedia.org/wiki/{terme_fr}'

    # Extraction du contenu de la page
    contenu_fr = obtenir_contenu_wikipedia(url_fr)

    # Filtrage pour ne garder que les paragraphes de définition
    # (Vous pouvez personnaliser cette partie selon la structure des pages Wikipedia)
    # Exemple : On garde les paragraphes qui contiennent le terme lui-même
    definition_fr = '\n'.join(
        [paragraphe for paragraphe in contenu_fr.split('\n') if terme_fr.lower() in paragraphe.lower()])

    # Traduction du terme et de la définition en arabe
    traducteur = Translator()
    terme_ar = traducteur.translate(terme_fr, dest='ar').text
    definition_ar = traduire_en_arabe(definition_fr)

    # Stockage des données
    donnees.append({
        'id': len(donnees) + 1,
        'terme_fr': terme_fr,
        'terme_ar': terme_ar,
        'lien_fr': url_fr,
        'lien_ar': f'https://ar.wikipedia.org/wiki/{terme_ar}',
        'definition_fr': definition_fr,
        'definition_ar': definition_ar
    })

# Écriture des données dans un fichier CSV
fichier_csv = 'extraction_donnees.csv'
champs = ['id', 'terme_fr', 'terme_ar', 'lien_fr', 'lien_ar', 'definition_fr', 'definition_ar']

with open(fichier_csv, 'w', newline='', encoding='utf-8') as csvfile:
    ecrivain = csv.DictWriter(csvfile, fieldnames=champs)

    # Écriture de l'en-tête
    ecrivain.writeheader()

    # Écriture des données
    for ligne in donnees:
        ecrivain.writerow(ligne)

print(f'Données extraites et stockées dans {fichier_csv}')
