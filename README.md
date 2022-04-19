# makeup-android

Este é um repositório que consome uma api de produtos cosméticos.

## API
Este aplicativo consome ```https://makeup-api.herokuapp.com/```.

## Arquitetura
Essa é a divisão simplificada de pastas
```
    -data
        -database
            - data_source
        -entities
            - product
                - repository
        -remote
    -di
    -domain
        -product
            -model
            -repository
        -product_favorite
            -repository
    -presentation
        -ui
            -detail
            -favorite
            -list
            -ultils
                -extensions
```

3 camadas principais<br/>
```data``` = Responsável por manipular os dados de api e banco de dados<br/>
```domain``` = Responsável por tratar os dados e fornecê-los para a camada de apresentação<br/>
```presentation``` = Responsável pela visualização dos dados<br/>

As implementações de repository ficam escondidas através de injeção de dependência, sendo o repository uma interface que precisa de outra classe que a
implemente, possuindo no final do nome "impl".

## Principais Bibliotecas
 - Shimmer ```https://github.com/facebook/shimmer-android```
 - Hilt ```https://developer.android.com/training/dependency-injection/hilt-android?hl=pt-br```
 - Glide ```https://github.com/bumptech/glide```
 - Retrofit ```https://github.com/square/retrofit```
 - Room ```https://developer.android.com/jetpack/androidx/releases/room```
 - Espresso ```https://developer.android.com/training/testing/espresso```
 - MockServer ```https://github.com/square/okhttp/tree/master/mockwebserver```

## Imagens do aplicativo
<p float="left">
<img src="https://github.com/paulowolking/makeupstore/blob/main/app/images/img.png" width="200" height="400" />
<img src="https://github.com/paulowolking/makeupstore/blob/main/app/images/img_1.png" width="200" height="400" />
<img src="https://github.com/paulowolking/makeupstore/blob/main/app/images/img_2.png" width="200" height="400" />
</p>
