const KAKAO_KEY="f361c14764dec8abd65706fb558dd90f";

const Kakao = axios.create({
    baseUrl:  "https://dapi.kakao.com",
    headers: {
        Authorization: "KakaoAK "+KAKAO_KEY
    }
});

const KakaoSearch=params => {
    return Kakao.get("/v3/search/book", {params })
}

const [books, setBooks] = useState([])

const getBooks=async() => {
    const search=value
    try{
        if(search === "") {
            setBooks([])
        } else {
            const params={
                query:search,
                size: 45,
                target: searchOption
            };
            const result = await kakaoSearch(params);

            if(result) {
                setBooks(result.data.document)
                navigator('/market', {state:result.data.documents})
            } else {
                console.log("fail")
            }
        }
    } catch(error) {
        console.log("error", error)
    }
}
