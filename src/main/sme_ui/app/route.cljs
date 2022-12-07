(ns sme-ui.app.route)

(def routes
"Defines the "
  [["/"
    {:name ::home
     :view h/home}]

   ["/set-builder"
    {:name ::set-builder
     :view set-builder}]
   
    ["/about"
     {:name ::about
      :view a/about}]
   
    ["/acks"
     {:name ::acks
      :view ack/acks}]])
